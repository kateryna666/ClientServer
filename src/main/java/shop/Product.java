package shop;

public class Product {
    private int id;
    private int groupId;
    private int amount;
    private double prise;
    private String name;



    public Product(int id, int groupId, int amount, double prise, String name) {
        this.id = id;
        this.groupId = groupId;
        this.amount = amount;
        this.prise = prise;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrise() {
        return prise;
    }

    public void setPrise(double prise) {
        this.prise = prise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", amount=" + amount +
                ", prise=" + prise +
                ", name='" + name + '\'' +
                '}';
    }
}
