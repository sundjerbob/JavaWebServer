package test.org.http.save_quote_service;
import java.io.IOException;
import java.net.ServerSocket;

public class QuotesServer {
    public static final int TCP_PORT_2 = 8114;
    public static void main(String[] args) {

        try( ServerSocket serverSocket = new ServerSocket(TCP_PORT_2) ) {

            while(true)
                new Thread(new QuotesServerThread(serverSocket.accept())).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
