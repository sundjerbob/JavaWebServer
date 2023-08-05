package test.org.http.quotes_service;


import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static final int TCP_PORT = 8113;
    public static void main(String[] args) {

        try( ServerSocket serverSocket = new ServerSocket(TCP_PORT) ) {

            while(true)
                new Thread(new ServerThread(serverSocket.accept())).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
