package escaperoom.model;

import room.model.Room;
import room.model.RoomBuilder;

import java.util.ArrayList;
import java.util.List;

public class EscapeRoomBuilder {
    private int id;
    private String name;

    private final List<Room> rooms = new ArrayList<>();

    public EscapeRoomBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public EscapeRoomBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EscapeRoomBuilder addRoom(Room room) {
        if (room != null) {
            this.rooms.add(room);
        }
        return this;
    }

    public EscapeRoomBuilder addRoom(RoomBuilder roomBuilder) {
        if (roomBuilder != null) {
            this.rooms.add(roomBuilder.build());
        }
        return this;
    }

    public EscapeRoom build() {
        EscapeRoom escapeRoom = new EscapeRoom(id, name);

        for (Room room : rooms) {
            escapeRoom.addRoom(room);
        }

        return escapeRoom;
    }

}
