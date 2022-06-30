package architecture;

import packege.Packet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Decryptor extends Thread{
    final static BlockingQueue<Packet> queuePackets = new ArrayBlockingQueue<>(20);

    public Decryptor(){ start();}

    public void decrypt(){
            try {
                int i = 0;
                while (true) {
                    byte[] bytes = ServerReceiver.queueBytes.poll(10L, TimeUnit.SECONDS);
                    Packet packet = PacketBuilder.decode(bytes);
                    System.out.println("Dec "+ (++i)+" "+packet);
                    queuePackets.put(packet);
                    if (ServerReceiver.queueBytes.isEmpty()) Thread.sleep(100L);
                    if (ServerReceiver.queueBytes.isEmpty()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
    @Override
    public void run() {
        decrypt();
    }
}
