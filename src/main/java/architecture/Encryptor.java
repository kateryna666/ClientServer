package architecture;

import Server.ServerTCP;
import packege.Packet;

import java.util.AbstractMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Encryptor extends Thread{
    final static BlockingQueue<AbstractMap.SimpleEntry<Integer, byte[]>> queueCodedAnswers = new ArrayBlockingQueue<>(ServerTCP.CAPACITY);
    private static boolean shutdown = false;
    public Encryptor(){start();}

    public void encrypt(){
            try {
                int i = 0;
                AbstractMap.SimpleEntry<Integer,Packet> ip;
                int userId;
                Packet packet;
                while (!shutdown) {
                    if(!Processor.queueProcessor.isEmpty()) {
                    ip = Processor.queueProcessor.poll(10L, TimeUnit.SECONDS);
                    userId = ip.getKey();
                    packet = ip.getValue();

                        queueCodedAnswers.put(new AbstractMap.SimpleEntry<>(userId, PacketBuilder.encode(packet)));
                        System.out.println("Enc " + (++i));
                    }
                    if (Processor.queueProcessor.isEmpty()) Thread.sleep(100L);
                    //if (Processor.queueProcessor.isEmpty()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("END OF ENC");
    }

    public static void shutdown(){
        shutdown = true;
    }

    @Override
    public void run() {
        encrypt();
    }
}
