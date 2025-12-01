    package escaperoom.service;

    import commonValueObjects.Id;
    import escaperoom.dao.EscapeRoomDao;
    import escaperoom.dao.EscapeRoomDaoImpl;
    import escaperoom.model.EscapeRoom;


    import java.util.List;
    import java.util.Optional;

    public class EscapeRoomService {
        private final EscapeRoomDaoImpl escapeRoomDaoImpl;

        public EscapeRoomService(EscapeRoomDaoImpl escapeRoomDaoImpl) {
            this.escapeRoomDaoImpl = escapeRoomDaoImpl;
        }

        public void createEscapeRoom(EscapeRoom escapeRoom){
            escapeRoomDaoImpl.save(escapeRoom);
        }

        public Optional<EscapeRoom> getEscapeRoomById(Id id) {
            return escapeRoomDaoImpl.findById(id);
        }

        public List<EscapeRoom> getAllEscapeRooms() {
            return escapeRoomDaoImpl.findAll();
        }

        public List<EscapeRoom> getEscapeRoomsByName(String name) {
            return escapeRoomDaoImpl.findByName(name);
        }

        public boolean updateEscapeRoom(EscapeRoom escapeRoom) {
            return escapeRoomDaoImpl.update(escapeRoom);
        }

        public boolean deleteEscapeRoom(Id id) {

            return escapeRoomDaoImpl.delete(id);
        }


    }
