package room.service;


import commonValueObjects.Id;
import commonValueObjects.Name;
import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.ObjectDecoration;
import room.dao.RoomDao;
import room.dao.RoomDaoImpl;
import room.model.Room;
import room.model.RoomBuilder;
import validators.RoomValidator;

import java.util.List;
import java.util.Optional;

public class RoomService {
    private final RoomDaoImpl roomDao;
    private ObjectDecorationDaoImpl decorationDao;

    public RoomService(RoomDaoImpl roomDao, ObjectDecorationDaoImpl objectDecorationDao){
        this.roomDao = roomDao;
        this.decorationDao = objectDecorationDao;
    }

    public RoomService(RoomDaoImpl roomDao) {
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

    public Id getIdByName(Name name) {
        return roomDao.getIdByName(name.toString())
                .map(Id::new)
                .orElseThrow(() -> new NotFoundException("Room not found with name: " + name));
    }
}
