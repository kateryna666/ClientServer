package practice1;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class PacketBuilder {
    final static String KEY= "sfjskfdksfldqwer";

    public byte[] encode(Packet packet) {
        return cryption(packet.toByte(), true);

    }
    public Packet decode(byte[] bytes){

        return new Packet(cryption(bytes, false));

    }

    public static void main(String[] args) {
            Massage massage = new Massage(1, 2, new byte[]{3,4,5});
            Packet packet = new Packet((byte) 6,7, massage);
            PacketBuilder packetBuilder = new PacketBuilder();
            byte[] test = packetBuilder.encode(packet);
            System.out.println(test.length);
            System.out.println(new String(test));
            System.out.println(packetBuilder.decode(test).toByte().length);
            System.out.println(packetBuilder.decode(test));
    }

    private byte[] cryption(byte[] packet, boolean encryptMode){

        try {
            Cipher cipher = Cipher.getInstance("AES");

            Key key =new SecretKeySpec(KEY.getBytes(), "AES");
            if(encryptMode)
                cipher.init(Cipher.ENCRYPT_MODE, key);
            else
                cipher.init(Cipher.DECRYPT_MODE, key);

            int massageOffset = 24;
            int length = packet.length-massageOffset-Short.BYTES;

            ByteBuffer buffer = ByteBuffer.wrap(packet);
            byte[] massage = new byte[length];
            buffer.get(massageOffset, massage, 0, length);
            massage = cipher.doFinal(massage);


            byte [] res = new byte[massageOffset+massage.length+Short.BYTES];
            ByteBuffer buffer1 = ByteBuffer.wrap(res);
            buffer1.put(packet, 0, massageOffset)
                    .put(massage)
                    .putShort( buffer.getShort(packet.length - Short.BYTES));
            return res;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }

    }

}
