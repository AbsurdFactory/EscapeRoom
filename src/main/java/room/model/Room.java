package room.model;

import clue.model.Clue;
import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.model.ObjectDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private Id id;
    private Name name;
    private Price price;
    private DifficultyLevel difficultyLevel;
    private List<Clue> clues = new ArrayList<>();
    private List<ObjectDecoration> objectDecorations = new ArrayList<>();
    private RoomEventPublisher publisher = new RoomEventPublisher();

    public Room() {
    }

    ;

    public Room(Name name, Price price, DifficultyLevel difficultyLevel) {
        this.name = name;
        this.price = price;
        this.difficultyLevel = difficultyLevel;
    }

    public Room(Id id, Name name, Price price, DifficultyLevel difficultyLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.difficultyLevel = difficultyLevel;
        this.publisher = (publisher != null)
                ? publisher
                : new RoomEventPublisher();

        this.publisher.notifyRoomCreated(this);
    }

    public Room(int id, Name name, Price price, DifficultyLevel difficultyLevel) {
        this.id = new Id(id);
        this.name = name;
        this.price = price;
        this.difficultyLevel = difficultyLevel;

        if (publisher != null) {
            publisher.notifyRoomCreated(this);
        }
    }
    public static Room rehydrate(int id, Name name, Price price, DifficultyLevel difficultyLevel) {
        return new Room(id, name, price, difficultyLevel);
    }



    public Id getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public DifficultyLevel getDifficultyLevel() {
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
        // Clue.getName(). once it's created.
        publisher.notifySubscribers("New clue added to room :" + name + "' → " + clue);

    }

    public void addDecorations(ObjectDecoration objectDecoration) {
        if (objectDecoration == null) {
            throw new IllegalArgumentException("The decoration cannot be null.");
        }
        objectDecorations.add(objectDecoration);
        publisher.notifySubscribers("New decoration added to room :" + name + "' → " + objectDecoration.getName());
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
