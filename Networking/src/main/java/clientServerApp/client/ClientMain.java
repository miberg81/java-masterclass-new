package clientServerApp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {

        // we want to connect to the server running on the same host(machine), the local computer
        // "localhost" maybe replaced with "127.0.0.1"
        // port number should be the same which the server is listening on
        try (Socket clientSocket = new Socket("localhost", 5000)) {

            // prepare the stream for expected server response with the echoed string
            BufferedReader echoesFromServerInput = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            // prepare string to send to server(which server will echo back in response).
            PrintWriter stringToEchoOutput = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // get the string for sending from the server via scanner
            // once we get the string we are going to write it to the socket
            Scanner scanner = new Scanner(System.in);
            String echoStringFromUser;
            String responseFromServer;

            // we use the do/while to make sure loop executes at least one
            // because we don't know how many iterations will be untill
            // user asks for "exit"
            do {
                System.out.println("Enter string to be echoed");
                echoStringFromUser = scanner.nextLine();

                stringToEchoOutput.println(echoStringFromUser);
                if (!echoStringFromUser.equals("exit")) {
                    responseFromServer = echoesFromServerInput.readLine();
                    System.out.println(responseFromServer);
                }
            } while (!echoStringFromUser.equals("exit"));

        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
