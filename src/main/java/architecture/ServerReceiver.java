package architecture;
import Server.MyServer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ServerReceiver extends Thread {
    final static BlockingQueue<byte[]> queueBytes = new ArrayBlockingQueue<>(20);

    public ServerReceiver(){
        start();
    }
    public void receiveMassage() {
            try {
                while (true) {
                    byte[] packet = MyServer.asking.poll(10L, TimeUnit.SECONDS);
                    queueBytes.put(packet);
                    System.out.println("Rec " + packet.toString());
                    if (MyServer.asking.isEmpty()) Thread.sleep(100L);
                    if (MyServer.asking.isEmpty()) break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void run() {
        receiveMassage();
    }
}
