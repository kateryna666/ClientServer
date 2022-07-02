package architecture;

import Server.ServerTCP;
import org.junit.jupiter.api.Test;

public class Powertest {
    @Test
    void timeCapacityTest(){
        try {
            for(int i = 0; i< ServerTCP.CAPACITY; i++){
                new ServerReceiver(FakeReceiver.generatePacket()).receiveMessage();
            }
            new Decryptor();
            for (int i = 0; i<ServerTCP.CAPACITY/20; i++) {
                new Processor();
            }
            new Encryptor();
            new Sender().join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n"+" END OF MAIN\n");
    }
}
