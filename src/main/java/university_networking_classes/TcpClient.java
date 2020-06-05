package university_networking_classes;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
    private final static int MAX_BUFFER_SIZE = 1024;

    public static void runClient(String host, int port) throws IOException {
        try (var scanner = new Scanner(System.in);
             var socket = new Socket(host, port); // CONNECTING
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new PrintStream(new BufferedOutputStream(socket.getOutputStream()))) {
            System.out.println("Your message to the server");
            String message = scanner.nextLine();
            out.println(message); // WRITING
            out.flush(); // FLUSHING

            char[] buffer = new char[MAX_BUFFER_SIZE];
            in.read(buffer); // READING

            System.out.println(buffer);
        }
    }

    public static void main(String[] args) throws IOException {
        runClient("localhost", 8080);
    }

}
