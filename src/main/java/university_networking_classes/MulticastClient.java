package university_networking_classes;

import java.net.*;

public class MulticastClient {
    public static void main(String[] args) {
        try (var socket = new MulticastSocket(8080);
        ) {
            InetAddress group = InetAddress.getByName("225.0.113.0");

            socket.joinGroup(group);

            DatagramPacket packet;  
            for (int i = 0; i < 5; i++) {
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData());
                System.out.println("Quote of the Moment: " + received);
            }

            socket.leaveGroup(group);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
