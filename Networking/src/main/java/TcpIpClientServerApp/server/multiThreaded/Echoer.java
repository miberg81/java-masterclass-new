package TcpIpClientServerApp.server.multiThreaded;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Echoer extends Thread {

    private Socket clientSocket;

    public Echoer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // prepare request from client reader
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            // prepare response to client writer
            PrintWriter output = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            while (true) {
                // This line is blocking, until input from client received
                // But the block only happens in this client's thread,all other clients
                // work separately
                final String inputString = input.readLine();
                System.out.println("Received client input: " + inputString);

                // delay the echoed response for 15 seconds(will not block other threads)
                // CLIENT WILL GET TIME OUT EXCEPTION WHEN SERVER SLEEPS HERE
//                try {
//                    Thread.sleep(15000);
//                } catch (InterruptedException e) {
//                    System.out.println("Thread interrupted");
//                }

                if(inputString.equals("exit")) {
                    break;
                }
                // echo back the original string
                output.println(inputString); // sending response!

                // This thread will terminate here, but the server will not be terminated,
                // because other connection may come, so server needed to be manually
                // shut down when needed.
            }

        } catch (IOException e) {
            System.out.println("Oops: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                // Oh, well
                //throw new RuntimeException(e);
            }
        }
    }
}
