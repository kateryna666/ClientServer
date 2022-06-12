package practice1;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class PacketBuilder {
    final static String KEY= "sfjskfdksfldqwer";

    public byte[] encode(Packet packet) {
        return cription(packet.toByte());

    }
    public Packet decode(byte[] bytes){

        return new Packet(cription(bytes));
    }

    public static void main(String[] args) {
        try {
            Massage massage = new Massage(1, 2, new byte[]{3,4,5});
            Packet packet = new Packet((byte) 6,7, massage);
            PacketBuilder packetBuilder = new PacketBuilder();
            System.out.println(packetBuilder.encode(packet).length);
            System.out.println(new String(packetBuilder.encode(packet)));
        }catch (RuntimeException e){
            System.out.println("Nothing work(((");
        }


    }

    private byte[] cription(byte[] data){

        try {
            Cipher cipher = Cipher.getInstance("AES");

            Key key = new Key() {
                @Override
                public String getAlgorithm() {
                    return KEY;
                }

                @Override
                public String getFormat() {
                    return KEY;
                }

                @Override
                public byte[] getEncoded() {
                    return new byte[0];
                }
            };
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int offset = 24;
            int length = data.length-8-24;

            return cipher.doFinal(data, offset, length);

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
