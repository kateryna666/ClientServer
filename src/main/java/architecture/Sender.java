package architecture;

import packege.Packet;
import shop.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.Arrays;

public class Sender {
    public static void sendMessage(byte[] massage, InetAddress target){
        System.out.println("User " + target.toString() + " received " +  new Packet(massage) + Thread.currentThread().getName());
    }
    public static void sendMessage(byte[] massage, ObjectOutputStream out) throws IOException {
        out.writeObject(massage);
        out.flush();
    }
}
