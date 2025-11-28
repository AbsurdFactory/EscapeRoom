package clue.service;

import clue.dao.ClueDaoImplementation;
import clue.model.Clue;
import commonValueObjects.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClueServiceTest {
    ClueDaoImplementation clueDao = new ClueDaoImplementation();
    ClueService clueService = new ClueService(clueDao);

    @Test
    @DisplayName("Validate if a Clue exist by passing the ClueName")
    void testValidateExistClueInDataBase() {

       assertFalse((clueService.validateExistClueInDataBase(new Name("patata"))));

       List <Clue> clueList =  clueService.getAllClues();

       assertTrue(clueService.validateExistClueInDataBase( clueList.getFirst().getName()));

    }
}