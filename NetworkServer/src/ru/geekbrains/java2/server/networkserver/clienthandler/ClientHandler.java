package ru.geekbrains.java2.server.networkserver.clienthandler;

import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.command.AuthCommand;
import ru.geekbrains.java2.client.command.BroadcastMessageCommand;
import ru.geekbrains.java2.client.command.PrivateMessageCommand;
import ru.geekbrains.java2.server.networkserver.DataBaseLogCatcher;
import ru.geekbrains.java2.server.networkserver.MyServer;
import ru.geekbrains.java2.server.networkserver.auth.AuthService;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static ru.geekbrains.java2.server.networkserver.MessageConstant.*;

public class ClientHandler {

    public static final int TIMEOUT_CLOSE_CONNECTION = 120_000;
    private final MyServer serverInstance;
    private final AuthService authService;
    private final Socket clientSocket;
    private DataBaseLogCatcher dblc = new DataBaseLogCatcher();


    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String nickname;


    public ClientHandler(Socket clientSocket, MyServer myServer) throws SQLException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.serverInstance = myServer;
        this.authService = serverInstance.getAuthService();
    }

    public void handle() throws IOException {
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                System.out.println("Connection has been failed");
            } finally {
                closeConnection();
            }
        }).start();
    }

    private void closeConnection() {
        try {
            serverInstance.unsubscribe(this);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }
            switch (command.getType()) {
                case END:
                    return;
                case BROADCAST_MESSAGE:
                    BroadcastMessageCommand data = (BroadcastMessageCommand) command.getData();
                    serverInstance.broadcastMessage(Command.messageCommand(nickname, data.getMessage()));
                    break;
                case PRIVATE_MESSAGE:
                    PrivateMessageCommand privateMessageCommand = (PrivateMessageCommand) command.getData();
                    String receiver = privateMessageCommand.getReceiver();
                    String message = privateMessageCommand.getMessage();
                    serverInstance.sendPrivateMessage(receiver, Command.messageCommand(nickname, message));
                    break;
                default:
                    String errorMessage = "Unknow type of command: " + command.getType();
                    System.err.println(errorMessage);
                    sendMessage(Command.errorCommand(errorMessage));
            }
        }
    }

    private Command readCommand() throws IOException {
        try {
            return (Command) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            String errorMessage = "Unknow type of object from client!";
            System.err.println(errorMessage);
            e.printStackTrace();
            sendMessage(Command.errorCommand(errorMessage));
            return null;
        }
    }

    private void authentication() throws IOException {
        Thread t = new Thread(() -> {
            try {
                sleep(TIMEOUT_CLOSE_CONNECTION);
                if (!Thread.currentThread().isInterrupted()){
                    dblc.insert("'Connection has been failed',");
                    closeConnection();
                }
            } catch (InterruptedException | SQLException e) {
                try {
                    dblc.insert("'Client connected', ");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Successful auth");
            }
        });
        t.start();


        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }
            switch (command.getType()) {
                case AUTH: {
                    if (processAuthCommand(command)) {
                        t.interrupt();
                        return;
                    }
                    break;
                }
                default:
                    String errorMessage = "Illegal command for authentification: " + command.getType();
                    System.err.println(errorMessage);
                    sendMessage(Command.errorCommand(errorMessage));
            }
        }
    }

    private boolean processAuthCommand(Command command) throws IOException {
        AuthCommand authCommand = (AuthCommand) command.getData();
        String login = authCommand.getLogin();
        String password = authCommand.getPassword();
        String nickname = authService.getNickByLoginAndPassword(login, password);
        if (nickname == null) {
            sendMessage(Command.authErrorCommand("Неверные логин/пароль!"));
        } else if (serverInstance.isNicknameBusy(nickname)) {
            sendMessage(Command.authErrorCommand("Учетная запись уже используется!"));
        } else {
            authCommand.setUsername(nickname);
            sendMessage(command);
            setNickname(nickname);
            serverInstance.broadcastMessage(Command.messageCommand(null, nickname + " зашел в чат!"));
            serverInstance.subscribe(this);
            return true;
        }
        return false;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMessage(Command command) throws IOException {
        outputStream.writeObject(command);
    }
}
