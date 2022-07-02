package architecture;

import UDP.ServerUDP;
import packege.Packet;
import shop.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import static UDP.ServerUDP.serverSocket;

public class Sender {
    public static void sendMessage(byte[] massage, InetAddress target, int port){
        DatagramPacket sendPacket = new DatagramPacket(massage, massage.length, target, port);
                try{
                    serverSocket.send(sendPacket);
                }catch (Exception e){
                    e.printStackTrace();
                }
        System.out.println("User " + target.toString() + " received " +  new Packet(massage) + Thread.currentThread().getName());
    }
    public static void sendMessage(byte[] massage, ObjectOutputStream out) throws IOException {
        out.writeObject(massage);
        out.flush();
    }
}
