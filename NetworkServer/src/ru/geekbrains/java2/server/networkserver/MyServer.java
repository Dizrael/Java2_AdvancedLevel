package ru.geekbrains.java2.server.networkserver;

import ru.geekbrains.java2.server.networkserver.auth.AuthService;
import ru.geekbrains.java2.server.networkserver.auth.BaseAuthService;
import ru.geekbrains.java2.server.networkserver.clienthandler.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class MyServer {

    private final int port;
    private final List<ClientHandler> clients;
    private final AuthService authService;
    private static final Logger serverLogger = Logger.getLogger(ru.geekbrains.java2.server.networkserver.
            MyServer.class.getName());
    private final Handler serverHandler = new FileHandler("NetworkServer/serverLog.log", true);


    public MyServer(int port) throws IOException {
        this.port = port;
        this.clients = new ArrayList<>();
        this.authService = new BaseAuthService();
    }

    public void start() {
        serverHandler.setLevel(Level.ALL);
        serverHandler.setFormatter(new SimpleFormatter());
        serverLogger.setLevel(Level.ALL);
        serverLogger.addHandler(serverHandler);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running");
            authService.start();
            serverLogger.log(Level.SEVERE, "Server have started");


            //noinspection InfiniteLoopStatement
            while (true) {
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();
//                System.out.println("Client has been connected");
                serverLogger.log(Level.INFO, "Client has been connected");
                ClientHandler handler = new ClientHandler(clientSocket, this);
                try {
                    handler.handle();
                } catch (IOException e) {
                    serverLogger.log(Level.SEVERE, "Failed to handle client connectiion!");
//                    System.err.println("Failed to handle client connectiion!");
                    clientSocket.close();
                }
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            authService.stop();
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

    public synchronized void broadcastMessage(String message) throws IOException {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}
