package room.model;

public class Room {
    private int id;
    private String name;
    private double price;
    private int difficultyLevel;

    public Room(){};

    public Room(int id, String name, double price, int difficultyLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.difficultyLevel = difficultyLevel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", difficultyLevel=" + difficultyLevel +
                '}';
    }
}
