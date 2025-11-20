package room.service;


import room.dao.RoomDao;
import room.model.Room;
import validators.RoomValidator;

import java.util.List;
import java.util.Optional;

public class RoomService {
    private final RoomDao roomDao;

    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public void createRoom(Room room) {
        RoomValidator.validateRoom(room);
        roomDao.save(room);
    }

    public Optional<Room> getRoomById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("The id must be greater than 0.");
        }

        return roomDao.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomDao.findAll();
    }

    public boolean updateRoom(Room room) {
        RoomValidator.validateRoom(room);
        return roomDao.update(room);
    }

    public boolean deleteRoom(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID.");
        }

        return roomDao.delete(id);

    }
}
