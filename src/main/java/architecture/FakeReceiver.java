package architecture;
import org.json.JSONObject;

import java.util.Random;

public class FakeReceiver implements Receiver {

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

    public void generateMassage(){
        JSONObject json = new JSONObject();
        int c = new Random().nextInt(4);
        json.put("command", c);
        switch (c){
            case 0: {json.put("pName", PRODUCTS[new Random().nextInt(4)]);}
            case 1: {json.put("pName", PRODUCTS[new Random().nextInt(4)]);
                json.put("gName", GROUPS[new Random().nextInt(2)]);}
            case 3: {json.put("gName", GROUPS[new Random().nextInt(2)]);}
        }

    }


    @Override
    public void receiveMassage() {


    }
}
