package clientServerApp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the server application, waiting for the client to connect
 */
public class ServerMain {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            // this code returns a different socket to each client
            // which connects to the server
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}