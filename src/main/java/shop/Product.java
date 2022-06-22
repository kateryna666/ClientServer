package shop;

public class Product {
    private int id;
    private static int statId = 1;
    private int group_id;
    private int amount = 0;
    private int prise = 0;
    private String name;

    public Product(int group_id, String name){
        this.id = statId;
        statId++;
        this.group_id = group_id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrise() {
        return prise;
    }

    public void setPrise(int prise) {
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
                ", group_id=" + group_id +
                ", amount=" + amount +
                ", prise=" + prise +
                ", name='" + name + '\'' +
                '}';
    }
}
