package room.model;

import clue.model.Clue;
import objectdecoration.model.ObjectDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private int id;
    private String name;
    private double price;
    private String difficultyLevel;
    private final List<Clue> clues = new ArrayList<>();
    private final List<ObjectDecoration> objectDecorations = new ArrayList<>();

    public Room() {
    }

    public Room(int id, String name, double price, String difficultyLevel) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be greater than zero.");
        }
        if (name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("The price cannot be negative.");
        }
        if (difficultyLevel.isEmpty() || difficultyLevel.isBlank()) {
            throw new IllegalArgumentException("The difficulty level cannot be empty.");
        }
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

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public List<Clue> getClues() {
        return Collections.unmodifiableList(clues);
    }

    public List<ObjectDecoration> getObjectDecorations() {
        return Collections.unmodifiableList(objectDecorations);
    }

    public void addClues(Clue clue) {
        if (clue == null) {
            throw new IllegalArgumentException("The clue cannot be empty");
        }
        clues.add(clue);
    }

    public void addDecorations(ObjectDecoration objectDecoration) {
        if (objectDecoration == null) {
            throw new IllegalArgumentException("The decoration cannot be null.");
        }
        objectDecorations.add(objectDecoration);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                ", clues=" + clues +
                ", objectDecorations=" + objectDecorations +
                '}';
    }
}
