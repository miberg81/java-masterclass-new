package TcpIpClientServerApp.server.singleThreaded;

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
public class ServerForSingleClientMain {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("server listening on port 5000");

            Socket clientSocket = serverSocket.accept();
            System.out.println("client connected");


            BufferedReader input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            // If true not specified, flush() should be called after every write to the stream
            // To ensure data is actually sent
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            // when the server reads "exit" from the client it will break out of reading
            while (true) {
                String echoString = input.readLine();
                if (echoString.equals("exit")) {
                    break;
                }
                output.println("Echo from server: " + echoString + " ,Michael");
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
            e.printStackTrace();
        }
    }
}