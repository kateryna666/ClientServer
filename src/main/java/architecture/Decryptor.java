package architecture;

import Server.ServerTCP;
import packege.Packet;

import java.util.AbstractMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Decryptor extends Thread{
    final static BlockingQueue<AbstractMap.SimpleEntry<Integer, Packet>> queuePackets = new ArrayBlockingQueue<>(ServerTCP.CAPACITY);
    private static boolean shutdown = false;

    public Decryptor(){ start();}

    public void decrypt(){
            try {
                int i = 0;
                while (!shutdown) {
                    try {
                        if(!ServerReceiver.queueBytes.isEmpty()) {
                        AbstractMap.SimpleEntry<Integer, byte[]> ib= ServerReceiver.queueBytes.poll(10L, TimeUnit.SECONDS);
                        int userId = ib.getKey();
                        byte[] bytes = ib.getValue();

                            Packet packet = PacketBuilder.decode(bytes);
                            System.out.println("Dec " + (++i) + " " + packet);
                            queuePackets.put(new AbstractMap.SimpleEntry<>(userId, packet));
                        }
                    } catch (IllegalStateException | NumberFormatException e){
                        System.err.println("Fake package");
                        //ignore fake packages
                    }
                    if (ServerReceiver.queueBytes.isEmpty()) Thread.sleep(100L);
                    //if (ServerReceiver.queueBytes.isEmpty()) break;


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("END OF DEC");
    }

    public static void shutdown(){
        shutdown = true;
    }

    @Override
    public void run() {
        decrypt();
    }
}
