package escaperoom.service;

import commonValueObjects.Id;
import escaperoom.dao.EscapeRoomDao;
import escaperoom.model.EscapeRoom;


import java.util.List;
import java.util.Optional;

public class EscapeRoomService {
    private final EscapeRoomDao escapeRoomDao;

    public EscapeRoomService(EscapeRoomDao escapeRoomDao) {
        this.escapeRoomDao = escapeRoomDao;
    }

    public void createEscapeRoom(EscapeRoom escapeRoom){
        escapeRoomDao.save(escapeRoom);
    }

    public Optional<EscapeRoom> getEscapeRoomById(Id id) {
        return escapeRoomDao.findById(id);
    }

    public List<EscapeRoom> getAllEscapeRooms() {
        return escapeRoomDao.findAll();
    }

    public boolean updateEscapeRoom(EscapeRoom escapeRoom) {
        return escapeRoomDao.update(escapeRoom);
    }

    public boolean deleteEscapeRoom(Id id) {

        return escapeRoomDao.delete(id);
    }
}
