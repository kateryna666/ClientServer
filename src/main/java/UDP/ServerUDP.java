package UDP;

import architecture.ReceiverUDP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//todo переробити branch_v2 з Map<Long, Pair<InetAddress, Integer>> clientMap
// Без шифрування працює - закоментити encoder;
public class ServerUDP {
    static final int PORT = 8082;
    static byte[] receiveData = null;
    static final int BUFFER_SIZE = 1024;
    protected static boolean die = false;
    public static DatagramSocket serverSocket;
    static {
        try {
            serverSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) throws IOException
    {
        System.out.println(" Started UDP server ");
        try {
            while (!die) {
                receiveData = new byte[BUFFER_SIZE];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                try {
                    new ReceiverUDP(receivePacket.getData(), receivePacket.getAddress(), receivePacket.getPort())
                            .receiveMassage();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            serverSocket.close();
        }
    }

}
