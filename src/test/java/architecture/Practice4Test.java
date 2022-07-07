package architecture;

import org.junit.jupiter.api.*;
import shop.Product;
import shop.Criteria;
import shop.ProductGroup;
import shop.ShopDBConnector;

import java.sql.SQLException;
import java.util.List;


public class Practice4Test {
    private static final ShopDBConnector connector = new ShopDBConnector();

    @BeforeAll
    static void initDb(){
        connector.initialization();
    }

    @BeforeEach
    void insertDb() throws SQLException {
        connector.insertGroup("group1");
        connector.insertGroup("group2");
        connector.insertProduct("product1", 10, 40, "group1" );
        connector.insertProduct("product2", 20, 30, "group1" );
        connector.insertProduct("product3", 30, 20, "group2" );
        connector.insertProduct("product4", 40, 10, "group2" );
    }

    @AfterEach
    void cleanUp() throws SQLException {
        connector.deleteAllProducts();
        connector.deleteAllGroups();
    }

    @Test
    void insertAll(){
        List<ProductGroup> groupList = connector.groupListByCriteria(new Criteria());
        assert (groupList.size()==2);
        List<Product> productList= connector.productListByCriteria(new Criteria());
        assert(productList.size()==4);


    }

    //DELETE:
    @Test
    void deleteAll() throws SQLException
    {
        connector.deleteGroup("group2");
        connector.deleteProduct("product3");

        List<ProductGroup> groupList = connector.groupListByCriteria(new Criteria());
        assert (groupList.size()==1);
        List<Product> productList= connector.productListByCriteria(new Criteria());
        assert(productList.size()==3);
    }


    @Test
    void updateAll() throws SQLException {
        connector.updateGroup("group2", "newGroup");
        connector.updateProduct("product3", "newProduct", 100, 20.2, "newGroup");

        List<ProductGroup> groupList = connector.groupListByCriteria(new Criteria().setName("newGroup"));
        assert (groupList.size() == 1);
        List<Product> productList = connector.productListByCriteria(new Criteria().setName("newProduct"));
        assert (productList.size() == 1);
    }
}
