package architecture;

import packege.Packet;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class Sender {
    //final static BlockingQueue<byte[]> queueCodedAnswers = new ArrayBlockingQueue<>(20);
    public void sendMessage(int target){
        new Thread(()->{
            try {
                int i = 0;
                while (true) {
                    byte[] message = Encryptor.queueCodedAnswers.poll(50L, TimeUnit.SECONDS);
                    System.out.println("Send "+(++i)+" User " + target + " received " +  new Packet(message));
                    if (Processor.queueProcessor.isEmpty()) Thread.sleep(500L);
                    if (Processor.queueProcessor.isEmpty()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
