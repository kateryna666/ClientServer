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
                synchronized (Manipulator.products) {
                    Manipulator.addProduct(productName, groupName);
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

    static Packet answer(Packet packet){

        String answer = "Ok";
        Message answerMessage = new Message(Command.ANSWER_OK.ordinal(), packet.getbMessage().getbUserId(), answer.getBytes());
        return  new Packet(packet.getbSrc(), packet.getbPktId(), answerMessage);
    }

    /*private void getAmount(String productName, long bPktId){
        try {
            System.out.println("Answer "+ bPktId+": "+ productName + "enable " + products.get(indexP(productName)).getAmount());
        } catch (NoSuchElementException e){
            System.out.println("Answer "+ bPktId+": No Such Element");
        }
    }

    private void addProduct(String productName, String toGroup, long bPktId){
        if(!groupExists(toGroup))
            addGroup(toGroup, bPktId);
        if(!productExists(productName)){
            products.add(new Product(groups.get(indexG(toGroup)).getId(), productName));
            System.out.println("Answer "+ bPktId+": "+ products.get(products.size()-1));
        }else System.out.println("Answer " + bPktId + ": " + "Product " + productName + " already exists!");
    }

    private void addGroup(String groupName, long bPktId){
        if(!groupExists(groupName)){
            groups.add(new ProductGroup(groupName));
            System.out.println("Answer "+ bPktId+": "+ products.get(products.size()-1));
        }else System.out.println("Answer " + bPktId + ": " + "Group " + groupName + " already exists!");
    }


    private void enrollProducts(String productName, int amount, long bPktId){
        try {
            int p = indexP(productName);
            products.get(p).setAmount(products.get(p).getAmount() + amount);
            System.out.println("Answer " + bPktId + ": " + products.get(p));
        }catch (NoSuchElementException e){
            System.out.println("Answer "+ bPktId+": No Such Element");
        }
    }

    private void delistProduct(String productName, int amount, long bPktId){
        try {
            int p = indexP(productName);
            int newAmount = products.get(p).getAmount() - amount;
            if(newAmount>=0){
                products.get(p).setAmount(newAmount);
                System.out.println("Answer " + bPktId + ": " + products.get(p));
            } else System.out.println("Answer " + bPktId + ": No Such Amount of "+ productName);

        }catch (NoSuchElementException e){
            System.out.println("Answer "+ bPktId+": No Such Element");
        }
    }

    private void setPrice(String productName, int newPrice, long bPktId){
        try {
            products.get(indexP(productName)).setPrise(newPrice);
            System.out.println("Answer " + bPktId + ": " + productName + " prise is " + newPrice);
        }catch (NoSuchElementException e){
            System.out.println("Answer "+ bPktId+": No Such Element");
        }
    }

    private int indexP(String productName){
        int j = -1;
        for (int i = 0; i<products.size(); i++) {
            if (products.get(i).getName().equals(productName))
                j = i;
        }
        if(j==-1)
            throw new NoSuchElementException();
        return j;
    }
    private int indexG(String groupName){
        int j = -1;
        for (int i = 0; i<groups.size(); i++) {
            if (groups.get(i).getName().equals(groupName))
                j = i;
        }
        if(j==-1)
            throw new NoSuchElementException();
        return j;
    }
    private boolean productExists(String productName){
        for (Product product : products) {
            if (product.getName().equals(productName))
                return true;
        }
        return false;
    }
    private boolean groupExists(String groupName){
        for (ProductGroup group : groups) {
            if (group.getName().equals(groupName))
                return true;
        }
        return false;
    }*/
}
