package architecture;
import org.json.JSONObject;
import packege.Message;
import packege.Packet;
import shop.Command;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public class FakeReceiver implements Receiver {
    public static Long pckId= 0L;
    private final int MAXPRICE = 20000;
    private final int MAXAMOUNT = 200;
    public static String[] PRODUCTS = new String[]{
            "Apples",
            "Bananas",
            "Tomato",
            "Cucumbers",
            "Salmon"
    };
    public static String[] GROUPS = new String[]{
            "Fruits",
            "Vegetables",
            "Meat"
    };

    public byte[] generatePacket(){
        JSONObject json = new JSONObject();
        Command command = Command.values()[new Random().nextInt(PRODUCTS.length-1)];
        json.put("command", command.ordinal());
        switch (command){
            case PRODUCT_AMOUNT -> {
                json.put("productName", PRODUCTS[new Random().nextInt(PRODUCTS.length-1)]);
            }
            case PRODUCT_CREATE -> {
                json.put("productName", PRODUCTS[new Random().nextInt(PRODUCTS.length-1)]);
                json.put("groupName", GROUPS[new Random().nextInt(GROUPS.length-1)]);
            }
            case GROUP_CREATE -> {
                json.put("groupName", GROUPS[new Random().nextInt(GROUPS.length-1)]);
            }
            case PRODUCT_INCREASE, PRODUCT_DECREASE -> {
                json.put("productName", PRODUCTS[new Random().nextInt(PRODUCTS.length-1)]);
                json.put("amount", new Random().nextInt(MAXAMOUNT));
            }
            case PRODUCT_PRICE -> {
                json.put("productName", PRODUCTS[new Random().nextInt(PRODUCTS.length-1)]);
                json.put("price", new Random().nextInt(MAXPRICE));
            }
        }
        try {
            synchronized (pckId) {
                return PacketBuilder.cryption(new Packet((byte) new Random().nextInt(Byte.MAX_VALUE),
                        pckId++, new Message(command.ordinal(),
                        new Random().nextInt(), json.toString().getBytes("utf-8"))).toByte(), true);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void receiveMassage(byte[] packet) {
        PacketBuilder.decode(generatePacket());

    }
}
