package architecture;

import Server.ServerTCP;

public class Main {
    public static void main(String[] args) {
        /*try {
            //new ServerReceiver(FakeReceiver.generatePacket()).receiveMessage();
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
        }*/

        System.out.println("\n"+" END OF MAIN\n");
    }
}
