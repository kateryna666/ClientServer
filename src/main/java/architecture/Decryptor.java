package architecture;

import Server.ServerTCP;
import packege.Packet;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Decryptor extends Thread{
    final static BlockingQueue<Packet> queuePackets = new ArrayBlockingQueue<>(ServerTCP.CAPACITY);

    public Decryptor(){ start();}

    public void decrypt(){
            try {
                int i = 0;
                while (true) {
                    try {
                        byte[] bytes = ServerReceiver.queueBytes.poll(10L, TimeUnit.SECONDS);
                        if(bytes !=null) {
                            Packet packet = PacketBuilder.decode(bytes);
                            System.out.println("Dec " + (++i) + " " + packet);
                            queuePackets.put(packet);
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
    @Override
    public void run() {
        decrypt();
    }
}
