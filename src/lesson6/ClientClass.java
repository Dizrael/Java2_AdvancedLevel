package lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static lesson6.ServerClass.SERVER_PORT;

public class ClientClass {

    private static final int CONNECTION_TIMEOUT = 10_000;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public ClientClass() {
        initServerConnection();
    }

    private void initServerConnection() {

        try {

            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", SERVER_PORT), CONNECTION_TIMEOUT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());


            processMessagesFromServer(socket);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void processMessagesFromServer(Socket socket)  {
        System.out.println("Соединение установлено!");
        System.out.println("Введите сообщение:\n");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {

                        String message = dataInputStream.readUTF();
                        if ("/end".equals(message)) {
                            System.out.println("Соединение с сервером было закрыто!\n");
                            break;
                        }
                        System.out.println("Server - " + message);
                    }

                } catch (IOException ex) {
                    System.out.println("Ситуация 1");
                    System.out.println("Соединение было закрыто!");
                }
            }

        }).start();
        Scanner sc = new Scanner(System.in);

        try {
            while (true) {
                String message = sc.nextLine();
                if (message.equals("/end")) {
                    socket.close();
                    sc.close();
                    break;
                }
                dataOutputStream.writeUTF(message);
            }
        } catch (IOException e) {
            System.out.println("Ситуация 2");
            System.out.println("Соединение было закрыто!");
        }

    }


    public static void main(String[] args) {
        ClientClass clientClass = new ClientClass();
    }

}
