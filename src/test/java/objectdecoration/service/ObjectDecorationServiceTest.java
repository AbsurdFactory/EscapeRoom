package objectdecoration.service;

import commonValueObjects.Id;
import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.model.ObjectDecoration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObjectDecorationServiceTest {

    private ObjectDecorationDao dao;
    private ObjectDecorationService service;

    @BeforeEach
    void setUp() {
        dao = mock(ObjectDecorationDao.class);
        service = new ObjectDecorationService(dao);
    }

    @Test
    void shouldAddDecorationSuccessfully() {
        service.addObjectDecoration("Candle Holder", "Metal", 120.5);
        verify(dao, times(1)).save(any(ObjectDecoration.class));
    }

    @Test
    void shouldFindDecorationById() {
        ObjectDecoration decoration = ObjectDecoration.rehydrate(1, "Candle Holder", "Metal", 120.5);
        when(dao.findById(new Id<>(1))).thenReturn(Optional.of(decoration));

        ObjectDecoration result = service.getById(new Id<>(1));

        assertEquals("Candle Holder", result.getName().toString());
        verify(dao, times(1)).findById(new Id<>(1));
    }

    @Test
    void shouldThrowExceptionWhenDecorationNotFound() {
        when(dao.findById(new Id<>(999))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.getById(new Id<>(999)));
    }

    @Test
    void shouldReturnAllDecorations() {
        when(dao.findAll()).thenReturn(List.of(
                ObjectDecoration.rehydrate(1, "Candle Holder", "Metal", 120.5)
        ));

        List<ObjectDecoration> result = service.getAll();

        assertEquals(1, result.size());
        verify(dao, times(1)).findAll();
    }

    @Test
    void shouldUpdateDecorationSuccessfully() {
        ObjectDecoration existing = ObjectDecoration.rehydrate(1, "Candle Holder", "Metal", 120.5);
        when(dao.findById(new Id<>(1))).thenReturn(Optional.of(existing));
        when(dao.update(any(ObjectDecoration.class))).thenReturn(true);

        boolean result = service.updateObjectDecoration(1, "Old Candle Holder", "Metal", 200.0);

        assertTrue(result);
        verify(dao, times(1)).update(any(ObjectDecoration.class));
    }
}

