package practice1;

import java.net.InetAddress;

public class Sender {
    public void sendMassage(byte[] massage, InetAddress target){
        System.out.println(massage+" "+ target+ " Ok");
    }
}
