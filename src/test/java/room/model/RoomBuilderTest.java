package room.model;

import clue.model.Clue;
import objectdecoration.model.ObjectDecoration;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RoomBuilderTest {

    @Test
    void build_ShouldCreateRoomWithBasicFields() {
        RoomBuilder builder = new RoomBuilder();

        Room room = builder
                .withId(1)
                .withName("Sala Misteriosa")
                .withPrice(25.5)
                .withDifficultyLevel("MEDIUM")
                .build();

        assertNotNull(room);
        assertEquals(1, room.getId());
        assertEquals("Sala Misteriosa", room.getName());
        assertEquals(25.5, room.getPrice());
        assertEquals("MEDIUM", room.getDifficultyLevel());
    }

    @Test
    void addClue_ShouldAddNonNullClue() {
        RoomBuilder builder = new RoomBuilder();

        Clue clue = mock(Clue.class);

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addClue(clue)
                .build();

        assertEquals(1, room.getClues().size());
        assertTrue(room.getClues().contains(clue));
    }

    @Test
    void addClue_ShouldIgnoreNullClue() {
        RoomBuilder builder = new RoomBuilder();

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addClue(null)
                .build();

        assertTrue(room.getClues().isEmpty());
    }

    @Test
    void addDecoration_ShouldAddNonNullDecoration() {
        RoomBuilder builder = new RoomBuilder();

        ObjectDecoration decoration = mock(ObjectDecoration.class);

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addDecoration(decoration)
                .build();

        assertEquals(1, room.getObjectDecorations().size());
        assertTrue(room.getObjectDecorations().contains(decoration));
    }

    @Test
    void addDecoration_ShouldIgnoreNullDecoration() {
        RoomBuilder builder = new RoomBuilder();

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addDecoration(null)
                .build();

        assertTrue(room.getObjectDecorations().isEmpty());
    }

    @Test
    void addDecorations_ShouldAddAllGivenDecorations() {
        RoomBuilder builder = new RoomBuilder();

        ObjectDecoration d1 = mock(ObjectDecoration.class);
        ObjectDecoration d2 = mock(ObjectDecoration.class);

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addDecorations(List.of(d1, d2))
                .build();

        assertEquals(2, room.getObjectDecorations().size());
        assertTrue(room.getObjectDecorations().contains(d1));
        assertTrue(room.getObjectDecorations().contains(d2));
    }

    @Test
    void addDecorations_ShouldIgnoreNullList() {
        RoomBuilder builder = new RoomBuilder();

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addDecorations(null)
                .build();

        assertTrue(room.getObjectDecorations().isEmpty());
    }

    @Test
    void addDecorations_ShouldIgnoreNullElementsInsideList() {
        RoomBuilder builder = new RoomBuilder();

        ObjectDecoration d1 = mock(ObjectDecoration.class);

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addDecorations(Arrays.asList(d1, null, null))
                .build();

        assertEquals(1, room.getObjectDecorations().size());
        assertTrue(room.getObjectDecorations().contains(d1));
    }

    @Test
    void build_ShouldAddCluesAndDecorationsToRoom() {
        RoomBuilder builder = new RoomBuilder();

        Clue clue = mock(Clue.class);
        ObjectDecoration deco = mock(ObjectDecoration.class);

        Room room = builder
                .withId(1)
                .withName("Test")
                .withPrice(10)
                .withDifficultyLevel("EASY")
                .addClue(clue)
                .addDecoration(deco)
                .build();

        assertEquals(1, room.getClues().size());
        assertEquals(1, room.getObjectDecorations().size());
        assertTrue(room.getClues().contains(clue));
        assertTrue(room.getObjectDecorations().contains(deco));
    }
}