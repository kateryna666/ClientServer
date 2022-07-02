package architecture;

import org.junit.jupiter.api.Test;
import packege.Massage;
import packege.Packet;

class PacketBuilderTest {

    @Test
    void ShouldHandelPackage(){
        PacketBuilder packetBuilder= new PacketBuilder();

        Massage massage = new Massage(1, 10,new byte[]{2, 100, 30} );
        Packet packet = new Packet((byte) 39,40, massage );
        /*byte[] encoded = packetBuilder.encode(packet);
        Packet decoded = packetBuilder.decode(encoded);
        Assertions.assertEquals(packet, decoded);*/
    }
}
