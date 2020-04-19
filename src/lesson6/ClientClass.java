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

    public ClientClass() throws IOException {
        processMessagesFromServer();
    }


    private void processMessagesFromServer() throws IOException {
        Socket socket = null;
        Thread thread = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", SERVER_PORT), CONNECTION_TIMEOUT);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Соединение установлено!");

            thread = createInputThread();
            runOutputLoop(dataOutputStream);

        } catch (IOException e) {
            System.out.println("Ситуация 2");
            System.out.println("Соединение было закрыто!");
        } finally {
            if (thread != null) createInputThread().interrupt();
            if (socket != null) socket.close();
        }

    }

    private void runOutputLoop(DataOutputStream out) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String message = sc.next();
            out.writeUTF(message);
            if (message.equals("/end")) {
                break;
            }
        }
    }

    private Thread createInputThread() {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String message = dataInputStream.readUTF();
                    System.out.println("Server - " + message);
                    if ("/end".equals(message)) {
                        System.exit(0);
                    }
                } catch (IOException ex) {
                    System.out.println("Соединение было закрыто!");
                    break;
                }
            }
        });
        thread.start();
        return thread;
    }


    public static void main(String[] args) throws IOException {
        ClientClass clientClass = new ClientClass();
    }

}
