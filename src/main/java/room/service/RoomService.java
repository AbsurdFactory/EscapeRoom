package room.service;


import commonValueObjects.Id;
import commonValueObjects.Name;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;
import room.dao.RoomDao;
import room.model.Room;
import room.model.RoomBuilder;
import validators.RoomValidator;

import java.util.List;
import java.util.Optional;

public class RoomService {
    private final RoomDao roomDao;
    private ObjectDecorationDao decorationDao;

    public RoomService(RoomDao roomDao, ObjectDecorationDao objectDecorationDao){
        this.roomDao = roomDao;
        this.decorationDao = objectDecorationDao;
    }

    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public void createRoom(Room room) {
        RoomValidator.validateRoom(room);
        roomDao.save(room);
    }

    public Optional<Room> getRoomById(Id id) {
        return roomDao.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }

    public boolean updateRoom(Room room) {
        RoomValidator.validateRoom(room);
        return roomDao.update(room);
    }

    public boolean deleteRoom(Id id) {

        return roomDao.delete(id);

    }

    public boolean deleteRoomByName(Name name) {
        return roomDao.deleteByName(name);
    }

    public Room createRoomWithAllDecorations(RoomBuilder builder) {
        List<ObjectDecoration> decorations = decorationDao.findAll();

        builder.addDecorations(decorations);

        Room room = builder.build();

        RoomValidator.validateRoom(room);

        roomDao.save(room);

        return room;
    }
}
