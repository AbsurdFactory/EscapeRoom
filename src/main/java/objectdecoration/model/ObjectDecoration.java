package objectdecoration.model;

public class ObjectDecoration {
    private int id;
    private String name;
    private String material;
    private double price;

    public ObjectDecoration() {
    }

    public ObjectDecoration(int id, String name, String material, double price) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ObjectDecoration{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", material='" + material + '\'' +
                ", price=" + price +
                '}';
    }
}
