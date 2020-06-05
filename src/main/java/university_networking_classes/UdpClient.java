package university_networking_classes;

import com.sun.security.jgss.GSSUtil;

import javax.xml.crypto.Data;
import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    private final static int MAX_BUFFER_SIZE = 1024;
    private final static int CLIENT_PORT = 8081;

    public static void main(String[] args) throws IOException {
        try (var clientSocket = new DatagramSocket(CLIENT_PORT)) { // SOCKET
            Console console = System.console();
            console.flush();

            String hostname = console.readLine("The servers hostname ");
            int serverPort = Integer.parseInt(console.readLine("The server port "));
            var serverAddress = InetAddress.getByName(hostname);
            String message = console.readLine("Your message to the server ");
            byte[] buffer = new byte[MAX_BUFFER_SIZE];
            System.arraycopy(message.getBytes(), 0, buffer, 0, message.length());

            var packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            clientSocket.send(packet); // SEND

            clientSocket.receive(packet); // RECEIVE
            packet = new DatagramPacket(buffer, buffer.length);
            System.out.printf("Response: %s\n", new String(packet.getData(), 0, packet.getLength()));
        }
    }
}