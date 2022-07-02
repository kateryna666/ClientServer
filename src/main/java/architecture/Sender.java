package architecture;

import Server.ServerTCP;
import packege.Packet;

import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Sender extends Thread {
    public Sender(){start();}
    public void sendMessage() {
            try {
                int target = 1000;
                int i = 0;
                while (true) {
                    byte[] message = Encryptor.queueCodedAnswers.poll(50L, TimeUnit.SECONDS);
                    if(message!=null) {
                        System.out.println("Send " + (++i) + " User " + target + " received " + Arrays.toString(message));
                    }else System.out.println("Send  Encryptor is empty " + i);
                    if (Encryptor.queueCodedAnswers.isEmpty()) Thread.sleep(500L);
                    //if (Encryptor.queueCodedAnswers.isEmpty()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("END OF SENDER");
    }

    @Override
    public void run(){
        sendMessage();
    }
}
