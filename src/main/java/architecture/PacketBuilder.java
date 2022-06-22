package architecture;

import packege.Packet;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class PacketBuilder {
    final static String KEY= "sfjskfdksfldqwer";

    public static void encode(Packet packet) {
        Sender.sendMessage(cryption(packet.toByte(), true),
                packet.getbMessage().getbUserId());

    }
    public static void decode(byte[] bytes){
        try {
            Processor.process(new Packet(cryption(bytes, false)));
        }catch (NumberFormatException e){
            return;
        }


    }


    private static byte[] cryption(byte[] packet, boolean encryptMode) throws RuntimeException {

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

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }

    }

}
