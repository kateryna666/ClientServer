package architecture;

import packege.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

public class ReceiverTCP implements Receiver {
    byte[] recRackage;
    private ObjectOutputStream out;

    public ReceiverTCP(byte[] packege, ObjectOutputStream out) {
        this.recRackage = packege;
        this.out = out;
    }

    @Override
    public void receiveMassage() throws IOException {
        try {
            Packet packet = PacketBuilder.decode(recRackage);
            if(packet!=null)
                Sender.sendMessage(PacketBuilder.encode(Processor.process(packet)), out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}