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
public class ServerForMultiClientMain {

    public static int clientNum = 1;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                System.out.println("server listening on port 5000");

                // PROBLEM1: The client only receives single echo message
                // Second message will not be echoes, untill next cleint connects
                // This line blocks!
                Socket clientSocket = serverSocket.accept();
                System.out.println("client " + clientNum + " connected");
                clientNum++;

                BufferedReader input = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));

                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                // PROBLEM1: This line blocks, if client doesn't send any lines!
                // Server will be stuck waiting here
                String echoString = input.readLine();

                // this time-out doesnt really solve the problem
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
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