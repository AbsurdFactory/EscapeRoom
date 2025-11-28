package objectdecoration.model;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;

import java.math.BigDecimal;

public class ObjectDecoration {
    private final Id<ObjectDecoration> id;
    private final Name name;
    private final Material material;
    private final Price price;

    private ObjectDecoration(Id<ObjectDecoration> id, Name name, Material material, Price price) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.price = price;
    }

    public static ObjectDecoration create(String name, String material, double price) {
        return new ObjectDecoration(null, new Name(name), new Material(material), new Price(new BigDecimal(price)));
    }

    public static ObjectDecoration rehydrate(int id, String name, String material, double price) {
        return new ObjectDecoration(new Id<>(id), new Name(name), new Material(material), new Price(new BigDecimal(price)));
    }

    public Id<ObjectDecoration> getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ObjectDecoration{" +
                "id=" + (id != null ? id.getValue() : "null") +
                ", name='" + name.toString() + '\'' +
                ", material='" + material.getValue() + '\'' +
                ", price=" + price.toBigDecimal() +
                '}';
    }
}
