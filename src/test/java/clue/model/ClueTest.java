package clue.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClueTest {
    @Test
    @DisplayName("Creating a new Clue by Strings and double")
    void testIfCanCreateAClueBasedOnStringsAndDouble(){
        Clue clue1 = new Clue("name1","test1","theme1",10.1);

        assertTrue(clue1 instanceof Clue);

    }
    @Test
    void getName() {
        Clue clue2 = new Clue("name2","test2","theme2",10.1);
        assertEquals("name2", clue2.getName().toString());

    }

    @Test
    void getText() {
        Clue clue2 = new Clue("name2","text2","theme2",10.1);
        assertEquals("text2", clue2.getText().toString());
    }

    @Test
    void getTheme() {
        Clue clue2 = new Clue("name2","text2","theme2",10.1);
        assertEquals("theme2", clue2.getTheme().toString());
    }

    @Test
    void getPrice() {
        Clue clue2 = new Clue("name2","text2","theme2",10.1);
        assertEquals(10.1, clue2.getPrice().toDouble());
    }

    @Test
    void testToString() {
        Clue clue2 = new Clue("name2","text2","theme2",10.1);
        assertTrue(clue2.toString().contains("name2"));
        assertTrue(clue2.toString().contains("text2"));
        assertTrue(clue2.toString().contains("theme2"));
    }
}