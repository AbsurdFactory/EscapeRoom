package room.model;

import clue.model.Clue;
import objectdecoration.model.ObjectDecoration;

import java.util.ArrayList;
import java.util.List;

public class RoomBuilder {

    private final List<Clue> clues = new ArrayList<>();
    private final List<ObjectDecoration> objectDecorations = new ArrayList<>();
    private int id;
    private String name;
    private double price;
    private String difficultyLevel;

    public RoomBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public RoomBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RoomBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public RoomBuilder withDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        return this;
    }


    public RoomBuilder addClue(Clue clue) {
        if (clue != null) {
            this.clues.add(clue);
        }
        return this;
    }

    public RoomBuilder addDecoration(ObjectDecoration decoration) {
        if (decoration != null) {
            this.objectDecorations.add(decoration);
        }
        return this;
    }

    public Room build() {
        Room room = new Room(id, name, price, difficultyLevel);

        for (Clue clue : clues) {
            room.addClues(clue);
        }
        for (ObjectDecoration decoration : objectDecorations) {
            room.addDecorations(decoration);
        }

        return room;
    }

}
