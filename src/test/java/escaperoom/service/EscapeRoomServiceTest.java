package escaperoom.service;

import commonValueObjects.Id;
import escaperoom.dao.EscapeRoomDaoImpl;
import escaperoom.model.EscapeRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EscapeRoomServiceTest {

    @Mock
    private EscapeRoomDaoImpl escapeRoomDaoImpl;

    private EscapeRoomService escapeRoomService;

    @BeforeEach
    void setUp() {
        escapeRoomService = new EscapeRoomService(escapeRoomDaoImpl);
    }

    @Test
    void testCreateEscapeRoom_Success() {
  
        EscapeRoom escapeRoom = mock(EscapeRoom.class);

  
        escapeRoomService.createEscapeRoom(escapeRoom);

  
        verify(escapeRoomDaoImpl, times(1)).save(escapeRoom);
    }

    @Test
    void testCreateEscapeRoom_WithNullEscapeRoom() {
  
        escapeRoomService.createEscapeRoom(null);

  
        verify(escapeRoomDaoImpl, times(1)).save(null);
    }

    @Test
    void testGetEscapeRoomById_WhenExists() {
  
        Id id = mock(Id.class);
        EscapeRoom escapeRoom = mock(EscapeRoom.class);
        when(escapeRoomDaoImpl.findById(id)).thenReturn(Optional.of(escapeRoom));

  
        Optional<EscapeRoom> result = escapeRoomService.getEscapeRoomById(id);

  
        assertTrue(result.isPresent());
        assertEquals(escapeRoom, result.get());
        verify(escapeRoomDaoImpl, times(1)).findById(id);
    }

    @Test
    void testGetEscapeRoomById_WhenNotExists() {
  
        Id id = mock(Id.class);
        when(escapeRoomDaoImpl.findById(id)).thenReturn(Optional.empty());

  
        Optional<EscapeRoom> result = escapeRoomService.getEscapeRoomById(id);

  
        assertFalse(result.isPresent());
        verify(escapeRoomDaoImpl, times(1)).findById(id);
    }

    @Test
    void testGetEscapeRoomById_WithNullId() {
  
        when(escapeRoomDaoImpl.findById(null)).thenReturn(Optional.empty());

  
        Optional<EscapeRoom> result = escapeRoomService.getEscapeRoomById(null);

  
        assertFalse(result.isPresent());
        verify(escapeRoomDaoImpl, times(1)).findById(null);
    }

    @Test
    void testGetAllEscapeRooms_WithMultipleRooms() {
  
        EscapeRoom room1 = mock(EscapeRoom.class);
        EscapeRoom room2 = mock(EscapeRoom.class);
        EscapeRoom room3 = mock(EscapeRoom.class);
        List<EscapeRoom> escapeRooms = Arrays.asList(room1, room2, room3);
        when(escapeRoomDaoImpl.findAll()).thenReturn(escapeRooms);

  
        List<EscapeRoom> result = escapeRoomService.getAllEscapeRooms();

  
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(escapeRooms, result);
        verify(escapeRoomDaoImpl, times(1)).findAll();
    }

    @Test
    void testGetAllEscapeRooms_WhenEmpty() {
  
        when(escapeRoomDaoImpl.findAll()).thenReturn(Collections.emptyList());

  
        List<EscapeRoom> result = escapeRoomService.getAllEscapeRooms();

  
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(escapeRoomDaoImpl, times(1)).findAll();
    }

    @Test
    void testUpdateEscapeRoom_Success() {
  
        EscapeRoom escapeRoom = mock(EscapeRoom.class);
        when(escapeRoomDaoImpl.update(escapeRoom)).thenReturn(true);

  
        boolean result = escapeRoomService.updateEscapeRoom(escapeRoom);

  
        assertTrue(result);
        verify(escapeRoomDaoImpl, times(1)).update(escapeRoom);
    }

    @Test
    void testUpdateEscapeRoom_Failure() {
  
        EscapeRoom escapeRoom = mock(EscapeRoom.class);
        when(escapeRoomDaoImpl.update(escapeRoom)).thenReturn(false);

  
        boolean result = escapeRoomService.updateEscapeRoom(escapeRoom);

  
        assertFalse(result);
        verify(escapeRoomDaoImpl, times(1)).update(escapeRoom);
    }

    @Test
    void testUpdateEscapeRoom_WithNullEscapeRoom() {
  
        when(escapeRoomDaoImpl.update(null)).thenReturn(false);

  
        boolean result = escapeRoomService.updateEscapeRoom(null);

  
        assertFalse(result);
        verify(escapeRoomDaoImpl, times(1)).update(null);
    }

    @Test
    void testDeleteEscapeRoom_Success() {
  
        Id id = mock(Id.class);
        when(escapeRoomDaoImpl.delete(id)).thenReturn(true);

  
        boolean result = escapeRoomService.deleteEscapeRoom(id);

  
        assertTrue(result);
        verify(escapeRoomDaoImpl, times(1)).delete(id);
    }

    @Test
    void testDeleteEscapeRoom_Failure() {
  
        Id id = mock(Id.class);
        when(escapeRoomDaoImpl.delete(id)).thenReturn(false);

  
        boolean result = escapeRoomService.deleteEscapeRoom(id);

  
        assertFalse(result);
        verify(escapeRoomDaoImpl, times(1)).delete(id);
    }

    @Test
    void testDeleteEscapeRoom_WithNullId() {
  
        when(escapeRoomDaoImpl.delete(null)).thenReturn(false);

  
        boolean result = escapeRoomService.deleteEscapeRoom(null);

  
        assertFalse(result);
        verify(escapeRoomDaoImpl, times(1)).delete(null);
    }

    @Test
    void testConstructor_WithValidDao() {
  
        EscapeRoomService service = new EscapeRoomService(escapeRoomDaoImpl);

  
        assertNotNull(service);
    }
}