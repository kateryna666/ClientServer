package architecture;

import packege.Packet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Encryptor {
    final static BlockingQueue<byte[]> queueCodedAnswers = new ArrayBlockingQueue<>(20);

    public void encrypt(){
        new Thread(()->{
            try {
                int i = 0;
                while (true) {
                    Packet packet = Processor.queueProcessor.poll(50L, TimeUnit.SECONDS);
                    byte[] codedPacket = PacketBuilder.encode(packet);
                    System.out.println("Enc "+ (++i)+" "+packet);
                    queueCodedAnswers.put(codedPacket);
                    if (Processor.queueProcessor.isEmpty()) Thread.sleep(500L);
                    if (Processor.queueProcessor.isEmpty()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
