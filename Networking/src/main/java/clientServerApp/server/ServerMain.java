package clientServerApp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This is the server application, waiting for the client to connect
 * When the client will connect, the server will use Input/Output streams
 * to send data to the client and receive data from the client
 */
public class ServerMain {

    public static void main(String[] args) {
        // create the server site socket
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("server listening on port 5000");
            // this code returns a different socket to each client, which connects to the server,
            // this code will wait until client try to connect. So if no client actually connected this code will not run
            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected");

            // Prepare input from client stream
            // Input stream is wrapped by a buffered reader for read efficiency.
            // buffered reader attached to the socket because,
            // you  want the server to remain alive until the client doesn't need it anymore
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            // Prepare output to client(response) stream
            // Output stream is wrapped in Print writer
            // If true not specified, flush() should be called after every write to the stream
            // To ensure data is actually sent
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // when the server reads "exit" from the client it will break out of reading
            while (true) {
                String echoString = input.readLine();
                if (echoString.equals("exit")) {
                    break;
                }
                // print the response that will be sent to client
                output.println("Echo from server: " + echoString);
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
            e.printStackTrace();
        }
    }
}