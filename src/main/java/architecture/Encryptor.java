package architecture;

import Server.ServerTCP;
import packege.Packet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Encryptor extends Thread{
    final static BlockingQueue<byte[]> queueCodedAnswers = new ArrayBlockingQueue<>(ServerTCP.CAPACITY);

    public Encryptor(){start();}

    public void encrypt(){
            try {
                int i = 0;
                while (true) {
                    Packet packet = Processor.queueProcessor.poll(50L, TimeUnit.SECONDS);
                    if(packet!=null) {
                        queueCodedAnswers.put(PacketBuilder.encode(packet));
                        System.out.println("Enc " + (++i));
                    }
                    if (Processor.queueProcessor.isEmpty()) Thread.sleep(500L);
                    //if (Processor.queueProcessor.isEmpty()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("END OF ENC");
    }
    @Override
    public void run() {
        encrypt();
    }
}
