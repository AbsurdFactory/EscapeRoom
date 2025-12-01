package escaperoom.model;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.model.Material;
import objectdecoration.model.ObjectDecoration;
import room.model.Room;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EscapeRoom {
    private Id id;
    private Name name;
    private List<Room> rooms;


    public EscapeRoom(Name name){
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public EscapeRoom(Id<EscapeRoom> id, Name name){

        this.id = id;
        this.name = name;
        this.rooms = new ArrayList<>();
    }

    public static EscapeRoom rehydrate(int id, String name) {
        return new EscapeRoom(new Id<>(id), new Name(name));
    }


    public Id getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public List<Room> getRooms(){
        return Collections.unmodifiableList(rooms);
    }

    public void addRoom(Room room){
        if (room == null){
            throw new IllegalArgumentException("The room cannot be null.");
        }
        rooms.add(room);
    }

    @Override
    public String toString() {
        return "EscapeRoom{" +
                "id=" + id.toString() +
                ", name='" + name.toString() + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
