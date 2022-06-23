package architecture;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

public class ThreadTest {
    @Test
    void ThreadsStopsInTime(){
        final int INITIAL = Thread.activeCount();
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
        assert (Thread.activeCount()==INITIAL);
    }
}
