package architecture;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerReceiver implements Receiver{
    final static BlockingQueue<byte[]> queueBytes = new ArrayBlockingQueue<>(20);
    @Override
    public void receiveMassage(byte[] packet) {
        new Thread(()->{

            try {
                queueBytes.put(packet);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
