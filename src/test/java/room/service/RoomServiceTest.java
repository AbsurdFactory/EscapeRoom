package room.service;

import commonValueObjects.Id;
import commonValueObjects.Name;
import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.ObjectDecoration;
import room.dao.RoomDaoImpl;
import room.model.Room;
import room.model.RoomBuilder;
import validators.RoomValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @Mock
    private RoomDaoImpl roomDao;

    @Mock
    private ObjectDecorationDaoImpl decorationDao;

    private RoomService roomService;

    @BeforeEach
    void setUp() {
        roomService = new RoomService(roomDao, decorationDao);
    }
    

    @Test
    void testConstructor_WithBothDaos() {
        
        RoomService service = new RoomService(roomDao, decorationDao);

        
        assertNotNull(service);
    }

    @Test
    void testConstructor_WithOnlyRoomDao() {
        
        RoomService service = new RoomService(roomDao);

        
        assertNotNull(service);
    }

   

    @Test
    void testCreateRoom_Success() {
        
        Room room = mock(Room.class);

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .then(invocation -> null);

            
            roomService.createRoom(room);

            
            mockedValidator.verify(() -> RoomValidator.validateRoom(room), times(1));
            verify(roomDao, times(1)).save(room);
        }
    }

    @Test
    void testCreateRoom_WithValidationException() {
        
        Room room = mock(Room.class);
        RuntimeException validationException = new IllegalArgumentException("Invalid room");

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .thenThrow(validationException);

            assertThrows(IllegalArgumentException.class, () -> roomService.createRoom(room));
            verify(roomDao, never()).save(any());
        }
    }

    @Test
    void testCreateRoom_WithNullRoom() {
        
        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(null))
                    .then(invocation -> null);

            
            roomService.createRoom(null);

            
            mockedValidator.verify(() -> RoomValidator.validateRoom(null), times(1));
            verify(roomDao, times(1)).save(null);
        }
    }


    @Test
    void testGetRoomById_WhenExists() {
        
        Id id = mock(Id.class);
        Room room = mock(Room.class);
        when(roomDao.findById(id)).thenReturn(Optional.of(room));

        
        Optional<Room> result = roomService.getRoomById(id);

        
        assertTrue(result.isPresent());
        assertEquals(room, result.get());
        verify(roomDao, times(1)).findById(id);
    }

    @Test
    void testGetRoomById_WhenNotExists() {
        
        Id id = mock(Id.class);
        when(roomDao.findById(id)).thenReturn(Optional.empty());

        
        Optional<Room> result = roomService.getRoomById(id);

        
        assertFalse(result.isPresent());
        verify(roomDao, times(1)).findById(id);
    }

    @Test
    void testGetRoomById_WithNullId() {
        
        when(roomDao.findById(null)).thenReturn(Optional.empty());

        
        Optional<Room> result = roomService.getRoomById(null);

        
        assertFalse(result.isPresent());
        verify(roomDao, times(1)).findById(null);
    }



    @Test
    void testGetAllRooms_WithMultipleRooms() {
        
        Room room1 = mock(Room.class);
        Room room2 = mock(Room.class);
        Room room3 = mock(Room.class);
        List<Room> rooms = Arrays.asList(room1, room2, room3);
        when(roomDao.findAll()).thenReturn(rooms);

        
        List<Room> result = roomService.getAllRooms();

        
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(rooms, result);
        verify(roomDao, times(1)).findAll();
    }

    @Test
    void testGetAllRooms_WhenEmpty() {
        
        when(roomDao.findAll()).thenReturn(Collections.emptyList());

        
        List<Room> result = roomService.getAllRooms();

        
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(roomDao, times(1)).findAll();
    }


    @Test
    void testUpdateRoom_Success() {
        
        Room room = mock(Room.class);
        when(roomDao.update(room)).thenReturn(true);

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .then(invocation -> null);

            
            boolean result = roomService.updateRoom(room);

            
            assertTrue(result);
            mockedValidator.verify(() -> RoomValidator.validateRoom(room), times(1));
            verify(roomDao, times(1)).update(room);
        }
    }

    @Test
    void testUpdateRoom_Failure() {
        
        Room room = mock(Room.class);
        when(roomDao.update(room)).thenReturn(false);

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .then(invocation -> null);

            
            boolean result = roomService.updateRoom(room);

            
            assertFalse(result);
            mockedValidator.verify(() -> RoomValidator.validateRoom(room), times(1));
            verify(roomDao, times(1)).update(room);
        }
    }

    @Test
    void testUpdateRoom_WithValidationException() {
        
        Room room = mock(Room.class);
        RuntimeException validationException = new IllegalArgumentException("Invalid room");

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .thenThrow(validationException);


            assertThrows(IllegalArgumentException.class, () -> roomService.updateRoom(room));
            verify(roomDao, never()).update(any());
        }
    }



    @Test
    void testDeleteRoom_Success() {
        
        Id id = mock(Id.class);
        when(roomDao.delete(id)).thenReturn(true);

        
        boolean result = roomService.deleteRoom(id);

        
        assertTrue(result);
        verify(roomDao, times(1)).delete(id);
    }

    @Test
    void testDeleteRoom_Failure() {
        
        Id id = mock(Id.class);
        when(roomDao.delete(id)).thenReturn(false);

        
        boolean result = roomService.deleteRoom(id);

        
        assertFalse(result);
        verify(roomDao, times(1)).delete(id);
    }

    @Test
    void testDeleteRoom_WithNullId() {
        
        when(roomDao.delete(null)).thenReturn(false);

        
        boolean result = roomService.deleteRoom(null);

        
        assertFalse(result);
        verify(roomDao, times(1)).delete(null);
    }



    @Test
    void testDeleteRoomByName_Success() {
        
        Name name = mock(Name.class);
        when(roomDao.deleteByName(name)).thenReturn(true);

        
        boolean result = roomService.deleteRoomByName(name);

        
        assertTrue(result);
        verify(roomDao, times(1)).deleteByName(name);
    }

    @Test
    void testDeleteRoomByName_Failure() {
        
        Name name = mock(Name.class);
        when(roomDao.deleteByName(name)).thenReturn(false);

        
        boolean result = roomService.deleteRoomByName(name);

        
        assertFalse(result);
        verify(roomDao, times(1)).deleteByName(name);
    }

    @Test
    void testDeleteRoomByName_WithNullName() {
        
        when(roomDao.deleteByName(null)).thenReturn(false);

        
        boolean result = roomService.deleteRoomByName(null);

        
        assertFalse(result);
        verify(roomDao, times(1)).deleteByName(null);
    }



    @Test
    void testCreateRoomWithAllDecorations_Success() {
        
        RoomBuilder builder = mock(RoomBuilder.class);
        Room room = mock(Room.class);
        ObjectDecoration decoration1 = mock(ObjectDecoration.class);
        ObjectDecoration decoration2 = mock(ObjectDecoration.class);
        List<ObjectDecoration> decorations = Arrays.asList(decoration1, decoration2);

        when(decorationDao.findAll()).thenReturn(decorations);
        when(builder.build()).thenReturn(room);

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .then(invocation -> null);

            
            Room result = roomService.createRoomWithAllDecorations(builder);

            
            assertNotNull(result);
            assertEquals(room, result);
            verify(decorationDao, times(1)).findAll();
            verify(builder, times(1)).addDecorations(decorations);
            verify(builder, times(1)).build();
            mockedValidator.verify(() -> RoomValidator.validateRoom(room), times(1));
            verify(roomDao, times(1)).save(room);
        }
    }

    @Test
    void testCreateRoomWithAllDecorations_WithEmptyDecorations() {
        
        RoomBuilder builder = mock(RoomBuilder.class);
        Room room = mock(Room.class);
        List<ObjectDecoration> decorations = Collections.emptyList();

        when(decorationDao.findAll()).thenReturn(decorations);
        when(builder.build()).thenReturn(room);

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .then(invocation -> null);

            
            Room result = roomService.createRoomWithAllDecorations(builder);

            
            assertNotNull(result);
            assertEquals(room, result);
            verify(decorationDao, times(1)).findAll();
            verify(builder, times(1)).addDecorations(decorations);
            verify(builder, times(1)).build();
            verify(roomDao, times(1)).save(room);
        }
    }

    @Test
    void testCreateRoomWithAllDecorations_WithValidationException() {
        
        RoomBuilder builder = mock(RoomBuilder.class);
        Room room = mock(Room.class);
        List<ObjectDecoration> decorations = Arrays.asList(mock(ObjectDecoration.class));
        RuntimeException validationException = new IllegalArgumentException("Invalid room");

        when(decorationDao.findAll()).thenReturn(decorations);
        when(builder.build()).thenReturn(room);

        try (MockedStatic<RoomValidator> mockedValidator = mockStatic(RoomValidator.class)) {
            mockedValidator.when(() -> RoomValidator.validateRoom(room))
                    .thenThrow(validationException);


            assertThrows(IllegalArgumentException.class,
                    () -> roomService.createRoomWithAllDecorations(builder));
            verify(decorationDao, times(1)).findAll();
            verify(builder, times(1)).addDecorations(decorations);
            verify(builder, times(1)).build();
            verify(roomDao, never()).save(any());
        }
    }

    @Test
    void testCreateRoomWithAllDecorations_WithNullBuilder() {
        
        when(decorationDao.findAll()).thenReturn(Collections.emptyList());


        assertThrows(NullPointerException.class,
                () -> roomService.createRoomWithAllDecorations(null));
        verify(decorationDao, times(1)).findAll();
        verify(roomDao, never()).save(any());
    }




    @Test
    void testGetIdByName_WhenExists() {

        Name name = new Name("Test Room");
        Integer idInt = 123;

        when(roomDao.getIdByName("Test Room")).thenReturn(Optional.of(idInt));

        Id result = roomService.getIdByName(name);

        assertNotNull(result);
        assertEquals(new Id(idInt), result);
        verify(roomDao, times(1)).getIdByName("Test Room");
    }

    @Test
    void testGetIdByName_WhenNotExists_ThrowsNotFoundException() {
        
        Name name = mock(Name.class);
        String nameString = "Non-existent Room";

        when(name.toString()).thenReturn(nameString);
        when(roomDao.getIdByName(nameString)).thenReturn(Optional.empty());


        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> roomService.getIdByName(name));

        assertTrue(exception.getMessage().contains("Room not found with name:"));
        assertTrue(exception.getMessage().contains(nameString));
        verify(roomDao, times(1)).getIdByName(nameString);
    }

    @Test
    void testGetIdByName_WithNullName() {
        assertThrows(NullPointerException.class,
                () -> roomService.getIdByName(null));
    }

    @Test
    void testGetIdByName_MultipleCallsWithSameName() {

        Name name = mock(Name.class);
        String nameString = "Test Room";
        Integer idInt = 123; // <-- ID real debe ser Integer

        when(name.toString()).thenReturn(nameString);
        when(roomDao.getIdByName(nameString)).thenReturn(Optional.of(idInt));

        Id result1 = roomService.getIdByName(name);
        Id result2 = roomService.getIdByName(name);

        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(new Id(idInt), result1);
        assertEquals(new Id(idInt), result2);

        verify(roomDao, times(2)).getIdByName(nameString);
    }
}