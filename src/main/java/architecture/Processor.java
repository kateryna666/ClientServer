package architecture;

import org.json.JSONObject;
import packege.Massage;
import packege.Packet;
import shop.Command;
import shop.Manipulator;

import java.io.UnsupportedEncodingException;


public class Processor {


    public static Packet process(Packet packet) throws UnsupportedEncodingException {
        Massage massage = packet.getbMessage();
        Command command = Command.values()[massage.getcType()];

        switch (command) {
            case PRODUCT_CREATE -> {
                JSONObject jsonObject = new JSONObject(new String(massage.getMessage()));
                String productName = (String) jsonObject.get("productName");
                String groupName = (String) jsonObject.get("groupName");
                synchronized (Manipulator.groups) {
                    synchronized (Manipulator.products) {
                        Manipulator.addProduct(productName, groupName);
                    }
                }
            }
            case PRODUCT_AMOUNT -> {
                JSONObject jsonObject = new JSONObject(new String(massage.getMessage()));
                String productName = (String) jsonObject.get("productName");
                synchronized (Manipulator.products) {
                    Manipulator.getAmount(productName);
                }
            }
            case PRODUCT_INCREASE -> {
                JSONObject jsonObject = new JSONObject(new String(massage.getMessage()));
                String productName = (String) jsonObject.get("productName");
                int amount = (int) jsonObject.get("amount");
                synchronized (Manipulator.products) {
                    Manipulator.enrollProduct(productName, amount);
                }
            }
            case PRODUCT_DECREASE -> {
                JSONObject jsonObject = new JSONObject(new String(massage.getMessage()));
                String productName = (String) jsonObject.get("productName");
                int amount = (int) jsonObject.get("amount");
                synchronized (Manipulator.products) {
                    Manipulator.derollProduct(productName, amount);
                }
            }

            case PRODUCT_PRICE -> {
                JSONObject jsonObject = new JSONObject(new String(massage.getMessage()));
                String productName = (String) jsonObject.get("productName");
                int price = (int) jsonObject.get("price");
                synchronized (Manipulator.products) {
                    Manipulator.setPrice(productName, price);
                }
            }
            case GROUP_CREATE -> {
                JSONObject jsonObject = new JSONObject(new String(massage.getMessage()));
                String groupName = (String) jsonObject.get("groupName");
                synchronized (Manipulator.groups) {
                    Manipulator.addGroup(groupName);
                }
            }
        }
        return answer(packet);
    }

    static Packet answer(Packet packet) throws UnsupportedEncodingException {

        String ANSWER = "Ok";
        Massage answerMassage = new Massage(Command.ANSWER_OK.ordinal(), packet.getbMessage().getbUserId(),
                new JSONObject().put("answer", ANSWER).toString().getBytes("utf-8"));
        return new Packet(packet.getbSrc(), packet.getbPktId(), answerMassage);
    }

}