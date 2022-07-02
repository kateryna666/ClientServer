package TCP;

import java.io.IOException;
import java.net.InetAddress;

public class ClientTCP {
    static final int MAX_THREADS = 5;

    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress addr = InetAddress.getByName("localhost");
        while (true) {
            if (ClientOneTCP.threadCount() < MAX_THREADS)
                new ClientOneTCP(addr);
            Thread.sleep(100);
        }
    }
}
