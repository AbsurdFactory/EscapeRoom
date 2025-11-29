package room.service;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.dao.ObjectDecorationDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import room.dao.RoomDao;
import room.model.DifficultyLevel;
import room.model.Room;
import room.model.RoomBuilder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RoomService Tests")
class RoomServiceTest {

    @Mock
    private RoomDao roomDao;

    @Mock
    private ObjectDecorationDao decorationDao;

    @InjectMocks
    private RoomService roomService;

    private Room validRoom;
    private Id validId;

    @BeforeEach
    void setUp() {
        validId = new Id(1);
        validRoom = new RoomBuilder()
                .withName(new Name("Mystery Room"))
                .withPrice(new Price(new BigDecimal("50.00")))
                .withDifficultyLevel(new DifficultyLevel("Medium"))
                .build();
    }


    @Test
    @DisplayName("Should create room successfully when valid room is provided")
    void shouldCreateRoomSuccessfully() {
        doNothing().when(roomDao).save(any(Room.class));

        assertDoesNotThrow(() -> roomService.createRoom(validRoom));

        verify(roomDao, times(1)).save(validRoom);
    }

    @Test
    @DisplayName("Should throw exception when creating room with null value")
    void shouldThrowExceptionWhenCreatingNullRoom() {

        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(null));
        verify(roomDao, never()).save(any(Room.class));
    }

    @Test
    @DisplayName("Should throw exception when room validation fails")
    void shouldThrowExceptionWhenRoomValidationFails() {

        Room invalidRoom = new RoomBuilder()
                .withName(null) // Invalid: null name
                .withPrice(new Price(new BigDecimal("50.00")))
                .withDifficultyLevel(new DifficultyLevel("Easy"))
                .build();


        assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(invalidRoom));
        verify(roomDao, never()).save(any(Room.class));
    }


    @Test
    @DisplayName("Should return room when valid ID is provided")
    void shouldReturnRoomWhenValidIdProvided() {
        when(roomDao.findById(validId)).thenReturn(Optional.of(validRoom));

        Optional<Room> result = roomService.getRoomById(validId);

        assertTrue(result.isPresent());
        assertEquals(validRoom, result.get());
        verify(roomDao, times(1)).findById(validId);
    }

    @Test
    @DisplayName("Should return empty Optional when room not found")
    void shouldReturnEmptyWhenRoomNotFound() {

        Id nonExistentId = new Id(999);
        when(roomDao.findById(nonExistentId)).thenReturn(Optional.empty());


        Optional<Room> result = roomService.getRoomById(nonExistentId);


        assertFalse(result.isPresent());
        verify(roomDao, times(1)).findById(nonExistentId);
    }


    @Test
    @DisplayName("Should return all rooms when rooms exist")
    void shouldReturnAllRoomsWhenRoomsExist() {

        Room room2 = new RoomBuilder()
                .withName(new Name("Horror Room"))
                .withPrice(new Price(new BigDecimal("60.00")))
                .withDifficultyLevel(new DifficultyLevel("Hard"))
                .build();

        List<Room> expectedRooms = Arrays.asList(validRoom, room2);
        when(roomDao.findAll()).thenReturn(expectedRooms);


        List<Room> result = roomService.getAllRooms();


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedRooms, result);
        verify(roomDao, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no rooms exist")
    void shouldReturnEmptyListWhenNoRoomsExist() {
        when(roomDao.findAll()).thenReturn(List.of());

        List<Room> result = roomService.getAllRooms();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roomDao, times(1)).findAll();
    }


    @Test
    @DisplayName("Should update room successfully when valid room is provided")
    void shouldUpdateRoomSuccessfully() {

        when(roomDao.update(validRoom)).thenReturn(true);


        boolean result = roomService.updateRoom(validRoom);


        assertTrue(result);
        verify(roomDao, times(1)).update(validRoom);
    }

    @Test
    @DisplayName("Should return false when room update fails")
    void shouldReturnFalseWhenUpdateFails() {

        when(roomDao.update(validRoom)).thenReturn(false);


        boolean result = roomService.updateRoom(validRoom);


        assertFalse(result);
        verify(roomDao, times(1)).update(validRoom);
    }

    @Test
    @DisplayName("Should throw exception when updating null room")
    void shouldThrowExceptionWhenUpdatingNullRoom() {

        assertThrows(IllegalArgumentException.class, () -> roomService.updateRoom(null));
        verify(roomDao, never()).update(any(Room.class));
    }


    @Test
    @DisplayName("Should delete room successfully when valid ID is provided")
    void shouldDeleteRoomSuccessfully() {

        when(roomDao.delete(validId)).thenReturn(true);

        boolean result = roomService.deleteRoom(validId);

        assertTrue(result);
        verify(roomDao, times(1)).delete(validId);
    }

    @Test
    @DisplayName("Should return false when room deletion fails")
    void shouldReturnFalseWhenDeletionFails() {
        Id nonExistentId = new Id(999);
        when(roomDao.delete(nonExistentId)).thenReturn(false);

        boolean result = roomService.deleteRoom(nonExistentId);

        assertFalse(result);
        verify(roomDao, times(1)).delete(nonExistentId);
    }


//    @Test
//    @DisplayName("Should create room with all decorations successfully")
//    void shouldCreateRoomWithAllDecorationsSuccessfully() {
//        // Arrange
//        RoomBuilder builder = new RoomBuilder()
//                .withName(new Name("Decorated Room"))
//                .withPrice(new Price(new BigDecimal("75.00")))
//                .withDifficultyLevel(new DifficultyLevel("Hard"));
//
//        ObjectDecoration decoration1 = new ObjectDecoration(
//                new Name("Chandelier"),
//                new Material("Crystal"),
//                new Price(new BigDecimal("100.00"))
//        );
//
//        ObjectDecoration decoration2 = new ObjectDecoration(
//                new Name("Painting"),
//                new Material("Canvas"),
//                new Price(new BigDecimal("50.00"))
//        );
//
//        List<ObjectDecoration> decorations = Arrays.asList(decoration1, decoration2);
//
//        when(decorationDao.findAll()).thenReturn(decorations);
//        doNothing().when(roomDao).save(any(Room.class));
//
//
//        RoomService roomServiceWithDecorations = new RoomService(roomDao, decorationDao);
//
//        // Act
//        Room result = roomServiceWithDecorations.createRoomWithAllDecorations(builder);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.getObjectDecorations().size());
//        verify(decorationDao, times(1)).findAll();
//        verify(roomDao, times(1)).save(any(Room.class));
//    }

    @Test
    @DisplayName("Should create room with no decorations when decoration list is empty")
    void shouldCreateRoomWithNoDecorationsWhenListIsEmpty() {
        // Arrange
        RoomBuilder builder = new RoomBuilder()
                .withName(new Name("Simple Room"))
                .withPrice(new Price(new BigDecimal("40.00")))
                .withDifficultyLevel(new DifficultyLevel("Easy"));

        when(decorationDao.findAll()).thenReturn(List.of());
        doNothing().when(roomDao).save(any(Room.class));

        RoomService roomServiceWithDecorations = new RoomService(roomDao, decorationDao);

        // Act
        Room result = roomServiceWithDecorations.createRoomWithAllDecorations(builder);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getObjectDecorations().size());
        verify(decorationDao, times(1)).findAll();
        verify(roomDao, times(1)).save(any(Room.class));
    }

    @Test
    @DisplayName("Should throw exception when builder is null in createRoomWithAllDecorations")
    void shouldThrowExceptionWhenBuilderIsNull() {
        // Arrange
        RoomService roomServiceWithDecorations = new RoomService(roomDao, decorationDao);

        // Act & Assert
        assertThrows(NullPointerException.class,
                () -> roomServiceWithDecorations.createRoomWithAllDecorations(null));
        verify(decorationDao, never()).findAll();
        verify(roomDao, never()).save(any(Room.class));
    }

    // ==================== CONSTRUCTOR TESTS ====================

    @Test
    @DisplayName("Should create RoomService with only RoomDao")
    void shouldCreateRoomServiceWithOnlyRoomDao() {
        // Act
        RoomService service = new RoomService(roomDao);

        // Assert
        assertNotNull(service);
    }

    @Test
    @DisplayName("Should create RoomService with both DAOs")
    void shouldCreateRoomServiceWithBothDaos() {
        // Act
        RoomService service = new RoomService(roomDao, decorationDao);

        // Assert
        assertNotNull(service);
    }
}