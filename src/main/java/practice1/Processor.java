package practice1;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Processor {
    private static ArrayList<ProductGroup> groups = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();

    public void process(Massage massage){
        //write synchronized blocs
        //manage process
        //manage process
    }

    private void getAmount(String productName, long bPktId){
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
    }
}
