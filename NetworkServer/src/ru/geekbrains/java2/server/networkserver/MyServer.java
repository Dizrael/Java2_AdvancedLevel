package ru.geekbrains.java2.server.networkserver;

import jdk.nashorn.internal.ir.LiteralNode;
import ru.geekbrains.java2.client.Command;
import ru.geekbrains.java2.client.command.AuthCommand;
import ru.geekbrains.java2.client.command.MessageCommand;
import ru.geekbrains.java2.client.command.PrivateMessageCommand;
import ru.geekbrains.java2.server.networkserver.auth.AuthService;
import ru.geekbrains.java2.server.networkserver.auth.BaseAuthService;
import ru.geekbrains.java2.server.networkserver.clienthandler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyServer {

    private final int port;
    private final List<ClientHandler> clients;
    private final AuthService authService;
    private final DataBaseLogCatcher dblc = new DataBaseLogCatcher();

    public MyServer(int port) throws SQLException, ClassNotFoundException {
        this.port = port;
        this.clients = new ArrayList<>();
        this.authService = new BaseAuthService();
        dblc.connect();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");
            authService.start();
            dblc.connect();
            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client has been connected");
                ClientHandler handler = new ClientHandler(clientSocket, this);
                try {
                    handler.handle();
                } catch (IOException e) {
                    System.err.println("Failed to handle client connectiion!");
                    clientSocket.close();
                }
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            authService.stop();
            dblc.disconnect();
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isNicknameBusy(String nickname) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMessage(Command command) throws IOException {
        for (ClientHandler client : clients) {
            client.sendMessage(command);
        }
    }

    public synchronized void sendPrivateMessage(String receiver, Command command) throws IOException{
        for (ClientHandler client : clients) {
            if(client.getNickname().equals(receiver)){
                client.sendMessage(command);
                return;
            }
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersList(users));
    }

    private List<String> getAllUsernames() {
//        return clients.stream()
//                .map(ClientHandler::getNickname)
//                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        for (ClientHandler client : clients) {
            result.add(client.getNickname());
        }
        return result;
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersList(users));
    }
}
