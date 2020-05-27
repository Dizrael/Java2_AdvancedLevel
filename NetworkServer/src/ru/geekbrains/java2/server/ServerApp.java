package ru.geekbrains.java2.server;

import ru.geekbrains.java2.server.networkserver.MyServer;

import java.io.IOException;

public class ServerApp {

    private static final int PORT = 8189;

    public static void main(String[] args) throws IOException {
        MyServer server = new MyServer(8189);
        server.start();
    }
}
