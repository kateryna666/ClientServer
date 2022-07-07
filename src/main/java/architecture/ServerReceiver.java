package architecture;
import Server.ServerTCP;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ServerReceiver{
    final static BlockingQueue<AbstractMap.SimpleEntry<Integer, byte[]>> queueBytes = new ArrayBlockingQueue<>(ServerTCP.CAPACITY);
    private byte[] potPackage;
    private int userId;

    public ServerReceiver(int userId, byte[] potPackage){
        this.potPackage= potPackage;
        this.userId = userId;
    }
    public void receiveMessage() {
            try {
                synchronized (queueBytes) {
                    queueBytes.put(new AbstractMap.SimpleEntry<>(userId, this.potPackage));
                    System.out.println("Rec " + Arrays.toString(this.potPackage));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

}
