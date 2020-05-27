package ru.geekbrains.java2.server.networkserver.clienthandler;

import ru.geekbrains.java2.server.networkserver.MyServer;
import ru.geekbrains.java2.server.networkserver.auth.AuthService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.*;

import static ru.geekbrains.java2.server.networkserver.MessageConstant.*;

public class ClientHandler {

    private final MyServer serverInstance;
    private final AuthService authService;
    private final Socket clientSocket;
    private static final Logger clientLogger = Logger.getLogger(ru.geekbrains.java2.server.networkserver.clienthandler.
            ClientHandler.class.getName());

    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private String nickname;
    private Handler clientLogHandler = new FileHandler("NetworkServer/serverClientLog.log", true);


    public ClientHandler(Socket clientSocket, MyServer myServer) throws IOException {
        this.clientSocket = clientSocket;
        this.serverInstance = myServer;
        this.authService = serverInstance.getAuthService();
        clientLogger.setLevel(Level.INFO);
        clientLogger.addHandler(clientLogHandler);
        clientLogHandler.setLevel(Level.INFO);
        clientLogHandler.setFormatter(new SimpleFormatter());
    }

    public void handle() throws IOException {
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());



        new Thread(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
//                System.out.println("Connection has been failed");
                clientLogger.log(Level.INFO, "Connection has been failed");
            } finally {
                closeConnection();
            }
        }).start();
    }

    private void closeConnection() {
        serverInstance.unsubscribe(this);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            String message = inputStream.readUTF();
            if (message.startsWith(END_CMD)) {
                return;
            }
            serverInstance.broadcastMessage(String.format("%s: %s", nickname, message));
            String logMessage = "User " + nickname + " have written message: *" + message + "*";
            clientLogger.log(Level.INFO, logMessage);
        }
    }

    private void authentication() throws IOException {
        while (true) {
            String message = inputStream.readUTF();
            if (message.startsWith(AUTH_CMD)) {
                String[] parts = message.split("\\s+");
                String login = parts[1];
                String password = parts[2];

                String nickname = authService.getNickByLoginAndPassword(login, password);
                if (nickname == null) {
                    sendMessage("Неверные логин/пароль!");
                }
                else if (serverInstance.isNicknameBusy(nickname)) {
                    sendMessage("Учетная запись уже используется!");
                }
                else {
                    sendMessage(String.format("%s %s", AUTH_SUCCESSFUL_CMD, nickname));
                    setNickname(nickname);
                    serverInstance.broadcastMessage(nickname + " Зашел в чат!");
                    serverInstance.subscribe(this);
                    break;
                }
            }
        }
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void sendMessage(String message) throws IOException {
        outputStream.writeUTF(message);
    }
}
