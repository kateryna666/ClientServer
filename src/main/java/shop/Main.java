package shop;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ShopDBConnector connector = new ShopDBConnector();
        connector.initialization();
        connector.deleteAllGroups();
        connector.deleteAllProducts();
        connector.insertGroup("Fruits");
        connector.insertProduct("Banana", 10, 30, "Fruits" );

        connector.insertGroup("Tomato");
        connector.updateGroup("Tomato","SuperTomato");
        System.out.println(connector.readGroup("SuperTomato"));
        connector.insertProduct("Juice", 10, 20, "SuperTomato" );
        //System.out.println(connector.productListByCriteria("GroupID", "<", 100));
    }
}
