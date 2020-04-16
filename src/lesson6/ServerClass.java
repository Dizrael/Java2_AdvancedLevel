package lesson6;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerClass {

    public static final int SERVER_PORT = 8189;

    public static void main(String[] args) {
        System.out.println("Старт эхо-сервера");
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Сервер запущен, ожидаем подключения...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился!");

            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

            Scanner sc = new Scanner(System.in);
            System.out.println("\nВы можете писать сообщения клиенту тут:\n");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        System.out.println("Запускаю поток");
                        while (true) {
                            //if (){
                                System.out.println("Захожу в цикл");
                                String messageToClient = sc.nextLine();
                                if ("/end".equals(messageToClient)) {
                                    sc.close();
                                    break;
                                }else if(!messageToClient.isEmpty()){
                                dataOutputStream.writeUTF(messageToClient);
                                }
                            //}
                        }
                        System.out.println("Вышел из цикла!");
                    } catch (IOException e) {
                        System.out.println("Cитуация 1");
                        e.getMessage();
                    }
                }
            }).start();

            try {
                while (true) {
                    String messageFromClient = dataInputStream.readUTF();
                    System.out.println("From client: " + messageFromClient);
                    if ("/end".equals(messageFromClient)) {
                        sc.close();
                        break;
                    }
                    dataOutputStream.writeUTF("Эхо - " + messageFromClient);
                }
            } catch (IOException e) {
                System.out.println("Cитуация 2");
                e.getMessage();
            }

            serverSocket.close();

        } catch (IOException e) {
            System.err.println("Port " + SERVER_PORT + " is already used");
            e.printStackTrace();
        }

    }
}
