package shop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopDBConnector {
    private Connection con;
    private final String name = "Shop";
    public void initialization() {
        try {

            con = DriverManager.getConnection("jdbc:sqlite:" + name);
            PreparedStatement st = con.prepareStatement(
                    "CREATE TABLE if NOT EXISTS 'ProductGroup' (" +
                            "'GroupID' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "'GroupName' VARCHAR(20) NOT NULL UNIQUE)");

            st.executeUpdate();

            st = con.prepareStatement(
                    "CREATE TABLE if NOT EXISTS 'Product' (" +
                            "'ProductID' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "'ProductName' VARCHAR(20) NOT NULL UNIQUE, " +
                            "'ProductAmount' INT DEFAULT 0, " +
                            "'ProductPrice' DECIMAL DEFAULT 0, " +
                            "'GroupID' INTEGER DEFAULT 0, " +
                            "CONSTRAINT 'GroupID' " +
                            "FOREIGN KEY ('GroupID') " +
                            "REFERENCES 'ProductGroup'('GroupID'))");

            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит");
            e.printStackTrace();
        }
    }

    public void insertGroup(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "INSERT INTO 'ProductGroup'(GroupName) VALUES (?)");

        statement.setString(1, name);

        statement.executeUpdate();
        statement.close();
    }

    public void insertProduct(String name, int amount, double price, String groupName) throws SQLException {
        PreparedStatement statement = con.prepareStatement("SELECT * FROM 'ProductGroup' WHERE GroupName = ?");
        statement.setString(1, groupName);
        ResultSet res = statement.executeQuery();

        statement = con.prepareStatement(
                "INSERT INTO Product(ProductName, ProductAmount, ProductPrice, GroupID) " +
                        "VALUES (?, ?, ?, ?)");

        statement.setString(1, name);
        statement.setInt(2, amount);
        statement.setDouble(3, price);
        statement.setInt(4, res.getInt("GroupID"));

        statement.executeUpdate();
        statement.close();
    }

    public Product readProduct(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM Product WHERE ProductName = ?");
        statement.setString(1, name);
        ResultSet res = statement.executeQuery();

        Product product = new Product(res.getInt("ProductID"),
                res.getInt("GroupID"),
                res.getInt("ProductAmount"),
                res.getDouble("ProductPrice"),
                res.getString("ProductName")
                );

        res.close();
        statement.close();

        return product;
    }
    public ProductGroup readGroup(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM ProductGroup WHERE GroupName = ?");
        statement.setString(1, name);
        ResultSet resGroup = statement.executeQuery();

        ProductGroup group = new ProductGroup(resGroup.getInt("GroupID"),
                resGroup.getString("GroupName"));


        resGroup.close();
        statement.close();

        return group;
    }

    public void updateProduct(String oldName, String newName, int amount, double price, String groupName) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "SELECT * FROM ProductGroup WHERE GroupName = ?");

        statement.setString(1, groupName);
        ResultSet resGroup = statement.executeQuery();

        statement = con.prepareStatement(
                "UPDATE Product SET ProductName = ?, ProductAmount = ?, ProductPrice = ?, GroupID = ? WHERE ProductName = ?");
        statement.setString(1, newName);
        statement.setInt(2, amount);
        statement.setDouble(3, price);
        statement.setInt(4, resGroup.getInt("GroupID"));
        statement.setString(5, oldName);

        statement.executeUpdate();
        statement.close();
    }
    public void updateGroup(String oldName, String newName) throws SQLException {

        PreparedStatement statement = con.prepareStatement(
                "UPDATE ProductGroup SET GroupName = ? WHERE GroupName = ?");

        statement.setString(2, oldName);
        statement.setString(1, newName);

        statement.executeUpdate();
        statement.close();
    }

    public void deleteGroup(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "DELETE FROM ProductGroup WHERE GroupName = ?;");

        statement.setString(1, name);
        statement.executeUpdate();

        statement.close();
    }
    public void deleteProduct(String name) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
                "DELETE FROM Product WHERE ProductName = ?");

        statement.setString(1, name);
        statement.executeUpdate();
        statement.close();
    }
    public void deleteAllGroups() throws SQLException {
        PreparedStatement statement = con.prepareStatement("DELETE FROM ProductGroup");
        statement.executeUpdate();

        statement.close();

    }
    public void deleteAllProducts() throws SQLException {
        PreparedStatement statement = con.prepareStatement("DELETE FROM Product");
        statement.executeUpdate();

        statement.close();
    }

    public List<ProductGroup> groupListByCriteria(Criteria criteria) {
        String where = null;
        if(criteria.getName()!=null) { where = " name like '%" + criteria.getName() + "%' ";}

        List<ProductGroup> list =new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String sql = where.equals(null)
                    ? "SELECT * FROM ProductGroup ;"
                    : "SELECT * FROM ProductGroup WHERE " + where+ " ;";

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                list.add(new ProductGroup(res.getInt("GroupID"),
                        res.getString("GroupName"))
                );
            }
            res.close();
            st.close();

        }catch(SQLException e){
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
        }
        return list;
    }


    public List<Product> productListByCriteria(Criteria criteria) {
        List<String> criterias = new ArrayList();
        if(criteria.getName()!=null) { criterias .add(" name like '%" + criteria.getName() + "%' ");}
        if(criteria.getPriceFrom()!=null){ criterias .add(" price >=" + criteria.getPriceFrom());}
        if(criteria.getPriceTill()!=null){ criterias .add(" price <=" + criteria.getPriceTill());}
        if(criteria.getAmountFrom()!=null){ criterias .add(" amount >=" + criteria.getAmountFrom());}
        if(criteria.getAmountTill()!=null){ criterias .add(" amount <=" + criteria.getAmountTill());}
        String where = String.join(" and", criterias );

        List<Product> list =new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String sql = criterias .isEmpty()
                    ? "SELECT * FROM Product ;"
                    : "SELECT * FROM Product WHERE " + where+" ;";

            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                list.add(new Product(res.getInt("ProductID"),
                        res.getInt("GroupID"),
                        res.getInt("ProductAmount"),
                        res.getDouble("ProductPrice"),
                        res.getString("ProductName"))
                );
                res.close();
                st.close();
            }
        }catch(SQLException e){
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
        }
        return list;
    }

}
