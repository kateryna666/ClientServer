package shop;

import java.util.ArrayList;
import java.util.Objects;

public class Manipulator {
    public static ArrayList<ProductGroup> groups = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();

    public static void getAmount(String productName) {
        Product product = findProduct(productName);
        if(product!=null) System.out.println(product);
    }

    public static void addProduct(String productName, String toGroup) {
        if (findGroup(toGroup)==null)
            addGroup(toGroup);
        if (findProduct(productName)==null)
            products.add(new Product(findGroup(toGroup).getId(), productName));
    }

    public static void addGroup(String groupName) {
        if (findGroup(groupName) == null)
            groups.add(new ProductGroup(groupName));
    }


    public static void enrollProduct(String productName, int amount) {
        int index = indexProduct(productName);
        if(index>-1){
            products.get(index).setAmount(products.get(index).getAmount() + amount);
        }


    }

    public static void derollProduct(String productName, int amount) {
        int index = indexProduct(productName);
        if(index>-1) {
            int newAmount = products.get(index).getAmount() - amount;
            if (newAmount >= 0)
                products.get(index).setAmount(newAmount);
        }
    }

    public static void setPrice(String productName, int newPrice) {
        int index = indexProduct(productName);
        if(index>-1)
            products.get(index).setPrise(newPrice);

    }

    private static Product findProduct(String productName) {
        for (Product product : products) {
            if (Objects.equals(product.getName(), productName)) {
                return product;
            }
        }
        return null;
    }
    private static ProductGroup findGroup(String groupName) {
        for (ProductGroup group : groups) {
            if (Objects.equals(group.getName(), groupName)) {
                return group;
            }
        }
        return null;
    }
    private static int indexProduct(String productName){
        int j = -1;
        for (int i = 0; i<products.size(); i++) {
            if (products.get(i).getName().equals(productName))
                j = i;
        }
        return j;
    }
}
