package TcpIpclientServerApp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        // we want to connect to the server running on the same host(machine), the local computer
        // "localhost" maybe replaced with "127.0.0.1"
        // port number should be the same which the server is listening on
        try (Socket clientSocket = new Socket("localhost", 5000)) {

            // throw SocketTimeoutException exception back to client
            // if server won't respond within timeout which is set here
            // Using timeout is very important,
            // it prevents the client from blocking forever, if server won't respond
            clientSocket.setSoTimeout(5000);

            // REQUEST: prepare string to send to server(which server will echo back in response).
            PrintWriter requestStream = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            // RESPONSE: prepare the stream for expected server response with the echoed string
            BufferedReader responseStream = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            // get the string for sending from the server via scanner
            // once we get the string we are going to write it to the socket
            Scanner scanner = new Scanner(System.in);
            String stringFromUser;
            String responseFromServer;
            // we use the do/while to make sure loop executes at least one
            // because we don't know how many iterations will be until
            // user asks for "exit"
            do {
                System.out.println("Enter string to be echoed");
                stringFromUser = scanner.nextLine();

                requestStream.println(stringFromUser); // string sent off to server!
                if (!stringFromUser.equals("exit")) {
                    responseFromServer = responseStream.readLine();
                    System.out.println(responseFromServer);
                }
            } while (!stringFromUser.equals("exit"));

         /*
            when client get this exception option are:
            1.Send request again(retry)
            2.Abort this operation with and inform the user.
            3.Terminate application
         */
        } catch (SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
