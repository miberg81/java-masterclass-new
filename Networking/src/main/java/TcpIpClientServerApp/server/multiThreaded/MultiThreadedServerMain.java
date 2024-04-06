package TcpIpClientServerApp.server.multiThreaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the server application, waiting for the client to connect
 * When the client will connect, the server will use Input/Output streams
 * to send data to the client and receive data from the client
 */
public class MultiThreadedServerMain {

    public static int clientNum = 1;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                System.out.println("server listening on port 5000");

                // create new client socket by accepting connection
                // This line is still blocking(until connection happens)
                // But since the connection is then handled in separate thread,
                // blocking on this line, does not prevent all the older connections to work
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Client " + clientNum + " connected");
                clientNum++;

                // pass this client socket connection to a new thread
                Echoer echoer = new Echoer(clientSocket);
                echoer.start(); // kick off the run method of the Echoer
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}