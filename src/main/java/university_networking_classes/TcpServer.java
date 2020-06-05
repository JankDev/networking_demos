package university_networking_classes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class TcpServer {
    private final static int MAX_BUFFER_SIZE = 1024;

    public static void runServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port); //BINDING

        System.out.println("Server started...");

        final String messageTemplate = "Received message: %s";

        while (true) {
            Socket client = serverSocket.accept(); // ACCEPTING

            CompletableFuture.runAsync(() -> {
                try (var in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     var out = new PrintStream(new BufferedOutputStream(client.getOutputStream()))) {
                    var buffer = new char[MAX_BUFFER_SIZE];

                    int bytesRead = in.read(buffer);

                    out.println(String.format(messageTemplate, new String(buffer))); // WRITE
                    out.flush(); // FLUSHING

                    client.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        }
    }

    public static void main(String[] args) throws IOException {
        runServer(8080);
    }
}
