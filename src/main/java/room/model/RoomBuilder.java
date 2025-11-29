package room.model;

import clue.model.Clue;
import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;

import java.util.ArrayList;
import java.util.List;

public class RoomBuilder {

    private Id id;
    private Name name;
    private Price price;
    private DifficultyLevel difficultyLevel;

    private final List<Clue> clues = new ArrayList<>();
    private final List<ObjectDecoration> objectDecorations = new ArrayList<>();


    public RoomBuilder withId(Id id) {
        this.id = id;
        return this;
    }

    public RoomBuilder withName(Name name) {
        this.name = name;
        return this;
    }

    public RoomBuilder withPrice(Price price) {
        this.price = price;
        return this;
    }

    public RoomBuilder withDifficultyLevel(DifficultyLevel difficultyLevel) {
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

    public RoomBuilder addDecorations(List<ObjectDecoration> decorations) {
        if (decorations != null) {
            decorations.stream()
                    .filter(d -> d != null)
                    .forEach(this.objectDecorations::add);
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
