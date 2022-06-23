package architecture;

import org.json.JSONObject;
import packege.Message;
import packege.Packet;
import shop.Command;
import shop.Manipulator;


public class Processor {


    public static void process(Packet packet) {
        Message message = packet.getbMessage();
        Command command = Command.values()[message.getcType()];

        switch (command) {
            case PRODUCT_CREATE -> {
                JSONObject jsonObject = new JSONObject(new String(message.getMessage()));
                String productName = (String) jsonObject.get("productName");
                String groupName = (String) jsonObject.get("groupName");
                synchronized (Manipulator.groups) {
                    synchronized (Manipulator.products) {
                        Manipulator.addProduct(productName, groupName);
                    }
                }
            }
            case PRODUCT_AMOUNT -> {
                JSONObject jsonObject = new JSONObject(new String(message.getMessage()));
                String productName = (String) jsonObject.get("productName");
                synchronized (Manipulator.products) {
                    Manipulator.getAmount(productName);
                }
            }
            case PRODUCT_INCREASE -> {
                JSONObject jsonObject = new JSONObject(new String(message.getMessage()));
                String productName = (String) jsonObject.get("productName");
                int amount = (int) jsonObject.get("amount");
                synchronized (Manipulator.products) {
                    Manipulator.enrollProduct(productName, amount);
                }
            }
            case PRODUCT_DECREASE -> {
                JSONObject jsonObject = new JSONObject(new String(message.getMessage()));
                String productName = (String) jsonObject.get("productName");
                int amount = (int) jsonObject.get("amount");
                synchronized (Manipulator.products) {
                    Manipulator.derollProduct(productName, amount);
                }
            }

            case PRODUCT_PRICE -> {
                JSONObject jsonObject = new JSONObject(new String(message.getMessage()));
                String productName = (String) jsonObject.get("productName");
                int price = (int) jsonObject.get("price");
                synchronized (Manipulator.products) {
                    Manipulator.setPrice(productName, price);
                }
            }
            case GROUP_CREATE -> {
                JSONObject jsonObject = new JSONObject(new String(message.getMessage()));
                String groupName = (String) jsonObject.get("groupName");
                synchronized (Manipulator.groups) {
                    Manipulator.addGroup(groupName);
                }
            }
        }
        PacketBuilder.encode(answer(packet));
    }

    static Packet answer(Packet packet) {

        String answer = "Ok";
        Message answerMessage = new Message(Command.ANSWER_OK.ordinal(), packet.getbMessage().getbUserId(), answer.getBytes());
        return new Packet(packet.getbSrc(), packet.getbPktId(), answerMessage);
    }

}