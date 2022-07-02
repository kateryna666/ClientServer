package architecture;

import packege.Packet;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class PacketBuilder {
    final static String KEY = "kfoeekfefkelorkb";

    public static byte[] encode(Packet packet) {
        /*Sender.sendMessage(cryption(packet.toByte(), true),
                packet.getbMessage().getbUserId());*/
        return cryption(packet.toByte(), true);

    }

    public static Packet decode(byte[] bytes) {
        return new Packet(cryption(bytes, false));
    }


    protected static byte[] cryption(byte[] packet, boolean encryptMode) throws RuntimeException {

        byte[] decodedKey = Base64.getUrlDecoder().decode(KEY);
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");

            int massageOffset = 16; //початок проміжку для шифрування
            int length = packet.length - massageOffset - Short.BYTES; //кінець проміжку
            ByteBuffer buffer = ByteBuffer.wrap(packet);
            byte[] massage = new byte[length];
            buffer.get(massageOffset, massage, 0, length); //повідомлення для шифрування
            byte[] crypredMassage;

            //дешифрування проміжку або зашифрування
            if (encryptMode) {
                cipher.init(Cipher.ENCRYPT_MODE, originalKey);
                crypredMassage = Base64.getEncoder().encode(cipher.doFinal(massage));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, originalKey);
                crypredMassage = cipher.doFinal(Base64.getDecoder().decode(massage));
            }

            byte[] res = new byte[massageOffset + crypredMassage.length + Short.BYTES]; //готовий масив байтів
            ByteBuffer buffer1 = ByteBuffer.wrap(res);
            buffer1.put(packet, 0, massageOffset)
                    .put(crypredMassage)
                    .putShort(buffer.getShort(packet.length - Short.BYTES));
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

