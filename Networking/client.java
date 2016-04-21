import java.io.*;
import java.net.*;

public class client {
    DatagramSocket Socket;

    public client() {

    }

    public void createAndListenSocket() {
        try {
            Socket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("10.208.20.207");
            byte[] incomingData = new byte[1024];

            this.est_connection(Socket,IPAddress);

            while(true)
            {
                String sentence = System.console().readLine("Message: ");
                byte[] data = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
                Socket.send(sendPacket);
                System.out.println("Message sent from client");
                DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
                Socket.receive(incomingPacket);
                String response = new String(incomingPacket.getData(),incomingPacket.getOffset(),incomingPacket.getLength(),"UTF-8");
                System.out.println("Response from server:" + response);
            }
            // Socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void est_connection(DatagramSocket Socket, InetAddress IPAddress) throws IOException
    {
        String sentence = new String("Trying to connect");
        byte[] data = sentence.getBytes();
        byte[] incomingData = new byte[1024];

        DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, 9876);
        Socket.send(sendPacket);
        // System.out.println("Message sent from client");
        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
        Socket.receive(incomingPacket);
        String response = new String(incomingPacket.getData());
        System.out.println("Response from server:" + response);
    }

    public static void main(String[] args) {
        client c_lient = new client();
        c_lient.createAndListenSocket();
    }
}
