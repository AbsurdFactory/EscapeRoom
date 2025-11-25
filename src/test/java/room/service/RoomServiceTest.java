package room.service;

import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import room.dao.RoomDao;
import room.model.Room;
import room.model.RoomBuilder;
import validators.RoomValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomDao roomDao;

    @Mock
    private ObjectDecorationDao decorationDao;

    @Mock
    private RoomBuilder roomBuilder;

    @Mock
    private Room room;


    @Test
    void createRoom_shouldValidateAndSaveRoom() {
        RoomService roomService = new RoomService(roomDao);

        try (MockedStatic<RoomValidator> validatorMock = mockStatic(RoomValidator.class)) {
            roomService.createRoom(room);

            validatorMock.verify(() -> RoomValidator.validateRoom(room));
            verify(roomDao, times(1)).save(room);
        }
    }


    @Test
    void getRoomById_withIdLessOrEqualZero_shouldThrowException() {
        RoomService roomService = new RoomService(roomDao);

        IllegalArgumentException ex1 = assertThrows(
                IllegalArgumentException.class,
                () -> roomService.getRoomById(0)
        );
        assertEquals("The id must be greater than 0.", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> roomService.getRoomById(-5)
        );
        assertEquals("The id must be greater than 0.", ex2.getMessage());
    }

    @Test
    void getRoomById_withValidId_shouldDelegateToDao() {
        RoomService roomService = new RoomService(roomDao);
        int id = 1;
        Optional<Room> expected = Optional.of(room);

        when(roomDao.findById(id)).thenReturn(expected);

        Optional<Room> result = roomService.getRoomById(id);

        assertEquals(expected, result);
        verify(roomDao, times(1)).findById(id);
    }


    @Test
    void getAllRooms_shouldReturnAllRoomsFromDao() {
        RoomService roomService = new RoomService(roomDao);

        List<Room> rooms = List.of(room);
        when(roomDao.findAll()).thenReturn(rooms);

        List<Room> result = roomService.getAllRooms();

        assertEquals(rooms, result);
        verify(roomDao, times(1)).findAll();
    }


    @Test
    void updateRoom_shouldValidateAndDelegateToDao() {
        RoomService roomService = new RoomService(roomDao);

        when(roomDao.update(room)).thenReturn(true);

        try (MockedStatic<RoomValidator> validatorMock = mockStatic(RoomValidator.class)) {
            boolean updated = roomService.updateRoom(room);

            assertTrue(updated);
            validatorMock.verify(() -> RoomValidator.validateRoom(room));
            verify(roomDao, times(1)).update(room);
        }
    }


    @Test
    void deleteRoom_withInvalidId_shouldThrowException() {
        RoomService roomService = new RoomService(roomDao);

        IllegalArgumentException ex1 = assertThrows(
                IllegalArgumentException.class,
                () -> roomService.deleteRoom(0)
        );
        assertEquals("Invalid ID.", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> roomService.deleteRoom(-10)
        );
        assertEquals("Invalid ID.", ex2.getMessage());
    }

    @Test
    void deleteRoom_withValidId_shouldDelegateToDao() {
        RoomService roomService = new RoomService(roomDao);
        int id = 3;

        when(roomDao.delete(id)).thenReturn(true);

        boolean result = roomService.deleteRoom(id);

        assertTrue(result);
        verify(roomDao, times(1)).delete(id);
    }


    @Test
    void createRoomWithAllDecorations_shouldBuildValidateAndSaveRoom() {

        RoomService roomService = new RoomService(roomDao, decorationDao);

        List<ObjectDecoration> decorations = List.of(
                mock(ObjectDecoration.class),
                mock(ObjectDecoration.class)
        );

        when(decorationDao.findAll()).thenReturn(decorations);
        when(roomBuilder.addDecorations(decorations)).thenReturn(roomBuilder);
        when(roomBuilder.build()).thenReturn(room);

        doNothing().when(roomDao).save(room);
        try (MockedStatic<RoomValidator> validatorMock = mockStatic(RoomValidator.class)) {
            Room result = roomService.createRoomWithAllDecorations(roomBuilder);

            assertNotNull(result);
            assertEquals(room, result);

            verify(decorationDao, times(1)).findAll();
            verify(roomBuilder, times(1)).addDecorations(decorations);
            verify(roomBuilder, times(1)).build();
            validatorMock.verify(() -> RoomValidator.validateRoom(room));
            verify(roomDao, times(1)).save(room);
        }
    }
}
