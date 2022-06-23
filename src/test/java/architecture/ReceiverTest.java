package architecture;
import org.junit.jupiter.api.Test;
import packege.Packet;

import java.util.ArrayList;

public class ReceiverTest {
    @Test
    void GoodAmount(){
        final int AMOUNT = 100;
        FakeReceiver fakeReceiver = new FakeReceiver();
        Thread[] threads = new Thread[AMOUNT];
        for (int i = 0; i < AMOUNT; i++) {
            threads[i] = new Thread(fakeReceiver::receiveMassage);
            threads[i].start();
        }
        for (int i = 0; i < AMOUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assert (FakeReceiver.pckId.shortValue() == AMOUNT);

        System.out.println("-----Test finish-----");

    }
}
