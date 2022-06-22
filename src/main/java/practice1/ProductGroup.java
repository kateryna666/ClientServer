package practice1;

public class ProductGroup {
    private int id;
    private static int statId = 1;
    private String name;

    public ProductGroup(String name) {
        this.id = statId;
        statId++;
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
