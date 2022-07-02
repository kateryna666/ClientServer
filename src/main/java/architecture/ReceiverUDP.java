package architecture;

import packege.Packet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceiverUDP implements Receiver{
    byte[] data;
    InetAddress target;
    int port;
    public ReceiverUDP(byte[] data, InetAddress target, int port) throws InterruptedException {
        this.data = data;
        this.target = target;
        this.port = port;
    }

    @Override
    public void receiveMassage() throws IOException {
        new Thread(() -> {
            try {
                Packet packet = PacketBuilder.decode(this.data);
                if (packet != null)
                    Sender.sendMessage(PacketBuilder.encode(Processor.process(packet)), this.target, this.port);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
