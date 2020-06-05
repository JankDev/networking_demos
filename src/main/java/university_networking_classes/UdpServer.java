package university_networking_classes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer {
    private final static int MAX_BUFFER_SIZE = 1024;
    private final DatagramSocket socket;

    public UdpServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port); // SOCKET
    }

    public void start() {
        System.out.println("Server started...");
        try{
            while(true){
                var receivedPacket = receive();
                send(receivedPacket);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } finally {socket.close();}

    }

    public DatagramPacket receive() throws IOException {
        byte[] buffer = new byte[MAX_BUFFER_SIZE];
        var packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet); // RECEIVE
        return packet;
    }

    public void send(DatagramPacket packet) throws IOException {
        packet.setData(String.format("Hello %s",packet.getAddress().getHostName()).getBytes());
        socket.send(packet); // SEND
    }

    public static void main(String[] args) throws SocketException {
        new UdpServer(8080).start();
    }
}
