package architecture;

import org.json.JSONObject;
import packege.Message;
import packege.Packet;
import shop.Command;
import shop.Manipulator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class Processor {
    final static BlockingQueue<Packet> queueProcessor = new ArrayBlockingQueue<>(20);

    public void process() {
        new Thread(()->{
            try {
                int i = 0;
                while (true) {
                    Packet packet;
                    synchronized (Decryptor.queuePackets){
                        packet = Decryptor.queuePackets.poll(10L, TimeUnit.SECONDS);
                    }
                    Message message = packet.getbMessage();
                    Command command = Command.values()[message.getcType()];
                    System.out.println("Pro "+ (++i)+" " + command);
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
                    queueProcessor.put(answer(packet));

                    if (Decryptor.queuePackets.isEmpty()) Thread.sleep(1000L);
                    if (Decryptor.queuePackets.isEmpty()) break;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }


    private Packet answer(Packet packet) {

        final String ANSWER = "Ok";
        Message answerMessage = new Message(Command.ANSWER_OK.ordinal(), packet.getbMessage().getbUserId(), ANSWER.getBytes());
        return new Packet(packet.getbSrc(), packet.getbPktId(), answerMessage);
    }

}