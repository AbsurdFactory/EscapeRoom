package room.service;


import clue.dao.ClueDaoImplementation;
import clue.service.ClueService;
import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.ObjectDecoration;
import objectdecoration.service.ObjectDecorationService;
import room.dao.RoomDao;
import room.dao.RoomDaoImpl;
import room.model.Room;
import room.model.RoomBuilder;
import validators.RoomValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class RoomService {
    private final RoomDaoImpl roomDao;
    private ObjectDecorationDaoImpl decorationDao;
    private ClueDaoImplementation clueDao;
    private ObjectDecorationService objectDecorationService;
    private ClueService clueService;

    public RoomService(RoomDaoImpl roomDao, ObjectDecorationDaoImpl objectDecorationDao) {
        this.roomDao = roomDao;
        this.decorationDao = objectDecorationDao;
        this.clueDao = new ClueDaoImplementation();
        this.objectDecorationService = new ObjectDecorationService(decorationDao);
        this.clueService = new ClueService(clueDao);

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

    public int countCluesInRoom(Id roomId) {
        return roomDao.countCluesByRoomId(roomId);
    }


    public int countDecorationsInRoom(Id roomId) {
        return roomDao.countDecorationsByRoomId(roomId);
    }


    public Price sumCluesPriceInRoom(Id roomId) {
        return roomDao.sumCluesPriceByRoomId(roomId);
    }


    public Price sumDecorationsPriceInRoom(Id roomId) {
        return roomDao.sumDecorationsPriceByRoomId(roomId);
    }


    public Price getTotalRoomPrice(Id roomId) {
        Room room = getRoomById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));

        Price cluesPrice = sumCluesPriceInRoom(roomId);
        Price decorationsPrice = sumDecorationsPriceInRoom(roomId);

        BigDecimal total = room.getPrice().toBigDecimal()
                .add(cluesPrice.toBigDecimal())
                .add(decorationsPrice.toBigDecimal());

        return new Price(total);
    }


    public RoomStatistics getRoomStatistics(Id roomId) {
        Room room = getRoomById(roomId)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + roomId));

        int clueCount = countCluesInRoom(roomId);
        int decorationCount = countDecorationsInRoom(roomId);
        Price cluesPrice = sumCluesPriceInRoom(roomId);
        Price decorationsPrice = sumDecorationsPriceInRoom(roomId);
        Price totalPrice = getTotalRoomPrice(roomId);

        return new RoomStatistics(room, clueCount, decorationCount, cluesPrice, decorationsPrice, totalPrice);
    }

    public void addClueToRoomByName(Name roomName, Name clueName) {

        int roomId = roomDao.getIdByName(roomName.toString())
                .orElseThrow(() -> new NotFoundException("Room not found: " + roomName));

        Id clueIdObj = clueService.getIdClueByName(clueName);
        if (clueIdObj == null) {
            throw new NotFoundException("Clue not found: " + clueName);
        }

        roomDao.addClueToRoom(roomId, clueIdObj.getValue());
    }

    public boolean removeClueFromRoomByName(Name roomName, Name clueName) {

        int roomId = roomDao.getIdByName(roomName.toString())
                .orElseThrow(() -> new NotFoundException("Room not found: " + roomName));

        Id clueIdObj = clueService.getIdClueByName(clueName);
        if (clueIdObj == null) {
            throw new NotFoundException("Clue not found: " + clueName);
        }

        int clueId = clueIdObj.getValue();

        return roomDao.removeClueFromRoom(roomId, clueId);
    }

    public void addDecorationToRoomByName(Name roomName, Name decorationName) {

        int roomId = roomDao.getIdByName(roomName.toString())
                .orElseThrow(() -> new NotFoundException("Room not found: " + roomName));

        Id decoIdObj = objectDecorationService.getIdObjectByName(decorationName);
        if (decoIdObj == null) {
            throw new NotFoundException("Decoration not found: " + decorationName);
        }

        roomDao.addDecorationToRoom(roomId, decoIdObj.getValue());
    }

    public boolean removeDecorationFromRoomByName(Name roomName, Name decorationName) {

        int roomId = roomDao.getIdByName(roomName.toString())
                .orElseThrow(() -> new NotFoundException("Room not found: " + roomName));

        Id decoIdObj = objectDecorationService.getIdObjectByName(decorationName);
        if (decoIdObj == null) {
            throw new NotFoundException("Decoration not found: " + decorationName);
        }

        return roomDao.removeDecorationFromRoom(roomId, decoIdObj.getValue());
    }

    public Name getNameById(Id id) {
        return roomDao.getNameById(id)
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + id));
    }
}
