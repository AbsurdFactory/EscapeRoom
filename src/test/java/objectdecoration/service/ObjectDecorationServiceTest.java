package objectdecoration.service;

import commonValueObjects.Id;
import commonValueObjects.Name;
import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.ObjectDecoration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ObjectDecorationServiceTest {

    private ObjectDecorationDaoImpl dao;
    private ObjectDecorationService service;

    @BeforeEach
    void setUp() {
        dao = mock(ObjectDecorationDaoImpl.class);
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

    @Test
    void shouldDeleteDecorationByIdSuccessfully() {
        Id<Integer> id = new Id<>(1);
        when(dao.delete(id)).thenReturn(true);

        boolean result = service.deleteObjectDecoration(id);

        assertTrue(result);
        verify(dao, times(1)).delete(id);
    }

    @Test
    void shouldDeleteDecorationByNameSuccessfully() {
        Name name = new Name("Candle Holder");
        when(dao.deleteByName(name)).thenReturn(true);

        boolean result = service.deleteObjectDecorationByName(name);

        assertTrue(result);
        verify(dao, times(1)).deleteByName(name);
    }
    @Test
    void shouldAddDecorationUsingObjectParameter() {
        ObjectDecoration decoration =
                ObjectDecoration.rehydrate(1, "Lantern", "Wood", 55.0);

        service.addObjectDecoration(decoration);

        verify(dao, times(1)).save(decoration);
    }
}

