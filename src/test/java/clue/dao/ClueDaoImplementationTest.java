package clue.dao;

import clue.model.Clue;
import clue.model.ClueName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ClueDaoImplementationTest {
    ClueDaoImplementation clueDaoImplementation = new ClueDaoImplementation();

    @Test
    void deleteClue() {
        Clue clue1 = new Clue(new ClueName("pista34"),"lalala","lolololo",30.0);
        List<Clue> list1 = clueDaoImplementation.findAll();
        clueDaoImplementation.deleteClue(clue1);
        List<Clue> list2 = clueDaoImplementation.findAll();

        assertFalse(list1.containsAll(list2));
    }


    @Test
    void getClueByName() {
        Clue clue2 = (clueDaoImplementation.getClueByName(new ClueName("pista40")));

        assertEquals("pista40", clue2.getName());
    }

    @Test
    void getAllCluesThemes() {
        List <String> themesList;
        themesList = clueDaoImplementation.getAllCluesThemes();

        assertFalse(themesList.isEmpty());
    }

    @Test
    void findAll() {
        List<Clue> clueList = clueDaoImplementation.findAll();

        assertFalse(clueList.isEmpty());
    }

    @Test
    void save() {
        Clue clue1 = new Clue(new ClueName("pista40"),"lalala","lolololo",30.0);
        clueDaoImplementation.save(clue1);
        Clue clue2 = (clueDaoImplementation.getClueByName(new ClueName(("pista40"))));
        assertEquals(Clue.class, clue2.getClass());
    }


    @Test
    void update() {
        Clue clue1 = clueDaoImplementation.getClueByName(new ClueName(("pista555")));
        Clue clue1bis = new Clue(new ClueName("pista555"),"lararirla","lalurari",40.0);
        clueDaoImplementation.update(clue1bis);
        Clue clue2 = (clueDaoImplementation.getClueByName(new ClueName("pista555")));
        assertEquals(clue2.getName(), clue1.getName());
        assertNotEquals(clue2.getPrice(), clue1.getPrice());
        assertNotEquals(clue2.getText(), clue1.getText());
    }


    @Test
    void deleteClueByName() {
        Clue clue1 = new Clue(new ClueName("pista32323"),"lalala","lolololo",30.0);
        List<Clue> list1 = clueDaoImplementation.findAll();
        clueDaoImplementation.deleteClueByName(new ClueName("pista32323"));
        List<Clue> list2 = clueDaoImplementation.findAll();

        assertFalse(list1.containsAll(list2));
    }
}