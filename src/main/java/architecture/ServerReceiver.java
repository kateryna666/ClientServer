package architecture;
import Server.MyServer;
import Server.ServerTCP;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ServerReceiver{
    final static BlockingQueue<byte[]> queueBytes = new ArrayBlockingQueue<>(ServerTCP.CAPACITY);
    byte[] potPackage;

    public ServerReceiver(byte[] potPackage){
        this.potPackage= potPackage;
    }
    public void receiveMessage() {
            try {
                synchronized (queueBytes) {
                    queueBytes.put(this.potPackage);
                    System.out.println("Rec " + Arrays.toString(this.potPackage));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

}
