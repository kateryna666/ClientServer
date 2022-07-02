package architecture;

import java.io.ObjectOutputStream;

public class ReceiverTCP implements Receiver{
    byte[] packet;
    ObjectOutputStream out;
    public ReceiverTCP(byte[] packet, ObjectOutputStream out){
        this.packet = packet;
        this.out = out;
    }
    private void send(){

    }
    @Override
    public void receiveMassage() {
        new ServerReceiver(packet).receiveMessage();
        if()
    }

}
