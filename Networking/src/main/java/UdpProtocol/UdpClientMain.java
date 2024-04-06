package UdpProtocol;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

/*
    Udp = User Datagram Protocol
    Is a "connectionless" protocol
    Used when speed is preferred over reliability(where losing a paket is not a disaster)
    Used in voice apps like Skype.
    It doesn't establish end to emd connection with server like tcp(via handshake),
    server is not establishing client socket for each client, like in TCP.
    Response from server is not required at all.
    The client sends the message and the "client's' address" is attached to each message
    This example down's use multithreading(real word app would use - see tcp example)

 */
public class UdpClientMain {

    public static void main(String[] args) {
	    try {
            // prepare the address of the server to send too. Here is localhost
            InetAddress address = InetAddress.getLocalHost();  // getByName()
            DatagramSocket datagramSocket = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);
            String echoString;

            do {
                System.out.println("Enter string to be echoed: " );
                echoString = scanner.nextLine();

                byte[] buffer = echoString.getBytes();

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 5000);
                datagramSocket.send(packet);

                /*
                Wait for response from client if the app requires it.
                Many times in UDP we don't even wait for any response, because it blocks.
                 */
                byte[] buffer2 = new byte[50];
                packet = new DatagramPacket(buffer2, buffer2.length);
                datagramSocket.receive(packet); // this line blocks.
                System.out.println("Text received is: " + new String(buffer2, 0, packet.getLength()));

            } while(!echoString.equals("exit"));

        } catch(SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch(IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
