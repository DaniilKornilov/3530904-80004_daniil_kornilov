package lab5;

public class Product {

    private final int id;
    private final int prodid;
    private final String title;
    private final int cost;

    public Product(int id, int prodid, String title, int cost) {
        this.id = id;
        this.prodid = prodid;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getProdid() {
        return prodid;
    }

    public String getTitle() {
        return title;
    }

    public int getCost() {
        return cost;
    }
}
