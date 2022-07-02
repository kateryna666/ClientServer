package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientTCP {
    static final int MAX_THREADS = 5;

    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress addr = InetAddress.getByName("localhost");
        int i = 0;
        while (i++<MAX_THREADS) {
            new ClientOneTCP(addr);
            Thread.currentThread().sleep(100);
        }
    }
}
