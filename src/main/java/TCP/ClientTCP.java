package TCP;

import java.io.IOException;
import java.net.InetAddress;

public class ClientTCP {
    static final int MAX_THREADS = 30;

    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress addr = InetAddress.getByName("localhost");
        while (true) {
            if (ClientOneTCP.threadCount() < MAX_THREADS) {
                new Thread(() -> {
                    for(int i = 0; i<30; i++) {
                        try {
                            new ClientOneTCP(addr);
                        } catch (InterruptedException e) {

                        }
                    }
                }).start();
            }else break;
        }
    }
}
