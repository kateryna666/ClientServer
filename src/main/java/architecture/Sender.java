package architecture;

import packege.Packet;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class Sender extends Thread{
    //final static BlockingQueue<byte[]> queueCodedAnswers = new ArrayBlockingQueue<>(20);
    public Sender(){start();}
    public void sendMessage() {
            try {
                int target = 1000;
                int i = 0;
                while (true) {
                    byte[] message = Encryptor.queueCodedAnswers.poll(100L, TimeUnit.SECONDS);
                    System.out.println("Send "+(++i)+" User " + target + " received " +  new Packet(message));
                    if (Processor.queueProcessor.isEmpty()) Thread.sleep(500L);
                    if (Processor.queueProcessor.isEmpty()) break;
                    //todo solve problem of last sending
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
    @Override
    public void run() {
        sendMessage();
    }
}
