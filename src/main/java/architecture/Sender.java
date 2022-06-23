package architecture;

import packege.Packet;
import shop.Command;
import java.net.InetAddress;
import java.util.Arrays;

public class Sender {
    public static void sendMessage(byte[] message, int target){
        System.out.println("User " + target + " received " +  new Packet(message) + Thread.currentThread().getName());
    }
}
