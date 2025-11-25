package escaperoom.model;

import room.model.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EscapeRoom {
    private int id;
    private String name;
    private List<Room> rooms;

    public EscapeRoom(){};

    public EscapeRoom(int id, String name){
        if(id <= 0) {
            throw new IllegalArgumentException("The id must be greater than zero.");
        }
        if(name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException("The name cannot be empty or blank.");
        }
        this.id = id;
        this.name = name;
        this.rooms = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public String getName() {
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
