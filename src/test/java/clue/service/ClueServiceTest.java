package clue.service;

import clue.dao.ClueDaoImplementation;
import clue.model.Clue;
import clue.model.ClueText;
import clue.model.ClueTheme;
import commonValueObjects.Name;
import commonValueObjects.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClueServiceTest {

    @Mock
    private ClueDaoImplementation clueDaoImplementation;

    private ClueService clueService;

    @BeforeEach
    void setUp() {
        clueService = new ClueService(clueDaoImplementation);
    }

    @Test
    void testCreateClue_WithParameters_Success() {
  
        Name name = mock(Name.class);
        ClueText text = mock(ClueText.class);
        ClueTheme theme = mock(ClueTheme.class);
        Price price = mock(Price.class);

        ArgumentCaptor<Clue> clueCaptor = ArgumentCaptor.forClass(Clue.class);

  
        clueService.createClue(name, text, theme, price);

  
        verify(clueDaoImplementation, times(1)).save(clueCaptor.capture());
        Clue capturedClue = clueCaptor.getValue();
        assertNotNull(capturedClue);
    }

    @Test
    void testCreateClue_WithClueObject_Success() {
  
        Clue clue = mock(Clue.class);

  
        clueService.createClue(clue);

  
        verify(clueDaoImplementation, times(1)).save(clue);
    }

    @Test
    void testCreateClue_WithNullClue() {
  
        clueService.createClue(null);

  
        verify(clueDaoImplementation, times(1)).save(null);
    }

    @Test
    void testValidateExistClueInDataBase_WhenClueExists() {
  
        Name name = mock(Name.class);
        Clue clue = mock(Clue.class);
        when(clueDaoImplementation.getClueByName(name)).thenReturn(clue);

  
        boolean result = clueService.validateExistClueInDataBase(name);

  
        assertTrue(result);
        verify(clueDaoImplementation, times(1)).getClueByName(name);
    }

    @Test
    void testValidateExistClueInDataBase_WhenClueDoesNotExist() {
  
        Name name = mock(Name.class);
        when(clueDaoImplementation.getClueByName(name)).thenReturn(null);

  
        boolean result = clueService.validateExistClueInDataBase(name);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).getClueByName(name);
    }

    @Test
    void testValidateExistClueInDataBase_WithNullName() {
  
        when(clueDaoImplementation.getClueByName(null)).thenReturn(null);

  
        boolean result = clueService.validateExistClueInDataBase(null);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).getClueByName(null);
    }

    @Test
    void testGetAllClues_WithMultipleClues() {
  
        Clue clue1 = mock(Clue.class);
        Clue clue2 = mock(Clue.class);
        Clue clue3 = mock(Clue.class);
        List<Clue> clues = Arrays.asList(clue1, clue2, clue3);
        when(clueDaoImplementation.findAll()).thenReturn(clues);

  
        List<Clue> result = clueService.getAllClues();

  
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(clues, result);
        verify(clueDaoImplementation, times(1)).findAll();
    }

    @Test
    void testGetAllClues_WhenEmpty() {
  
        when(clueDaoImplementation.findAll()).thenReturn(Collections.emptyList());

  
        List<Clue> result = clueService.getAllClues();

  
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(clueDaoImplementation, times(1)).findAll();
    }

    @Test
    void testGetAllThemes_WithMultipleThemes() {
  
        List<String> themes = Arrays.asList("Mystery", "Horror", "Adventure");
        when(clueDaoImplementation.getAllCluesThemes()).thenReturn(themes);

  
        List<String> result = clueService.getAllThemes();

  
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(themes, result);
        verify(clueDaoImplementation, times(1)).getAllCluesThemes();
    }

    @Test
    void testGetAllThemes_WhenEmpty() {
  
        when(clueDaoImplementation.getAllCluesThemes()).thenReturn(Collections.emptyList());

  
        List<String> result = clueService.getAllThemes();

  
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(clueDaoImplementation, times(1)).getAllCluesThemes();
    }

    @Test
    void testGetAllThemes_WithDuplicates() {
  
        List<String> themes = Arrays.asList("Mystery", "Horror", "Mystery");
        when(clueDaoImplementation.getAllCluesThemes()).thenReturn(themes);

  
        List<String> result = clueService.getAllThemes();

  
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(clueDaoImplementation, times(1)).getAllCluesThemes();
    }

    @Test
    void testUpdateClue_Success() {
  
        Clue clue = mock(Clue.class);
        when(clueDaoImplementation.update(clue)).thenReturn(true);

  
        boolean result = clueService.updateClue(clue);

  
        assertTrue(result);
        verify(clueDaoImplementation, times(1)).update(clue);
    }

    @Test
    void testUpdateClue_Failure() {
  
        Clue clue = mock(Clue.class);
        when(clueDaoImplementation.update(clue)).thenReturn(false);

  
        boolean result = clueService.updateClue(clue);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).update(clue);
    }

    @Test
    void testUpdateClue_WithNullClue() {
  
        when(clueDaoImplementation.update(null)).thenReturn(false);

  
        boolean result = clueService.updateClue(null);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).update(null);
    }

    @Test
    void testDeleteClueByName_Success() {
  
        Name name = mock(Name.class);
        when(clueDaoImplementation.deleteByName(name)).thenReturn(true);

  
        boolean result = clueService.deleteClueByName(name);

  
        assertTrue(result);
        verify(clueDaoImplementation, times(1)).deleteByName(name);
    }

    @Test
    void testDeleteClueByName_Failure() {
  
        Name name = mock(Name.class);
        when(clueDaoImplementation.deleteByName(name)).thenReturn(false);

  
        boolean result = clueService.deleteClueByName(name);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).deleteByName(name);
    }

    @Test
    void testDeleteClueByName_WithNullName() {
  
        when(clueDaoImplementation.deleteByName(null)).thenReturn(false);

  
        boolean result = clueService.deleteClueByName(null);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).deleteByName(null);
    }

    @Test
    void testDeleteClue_Success() {
  
        Clue clue = mock(Clue.class);
        when(clueDaoImplementation.deleteClue(clue)).thenReturn(true);

  
        boolean result = clueService.deleteClue(clue);

  
        assertTrue(result);
        verify(clueDaoImplementation, times(1)).deleteClue(clue);
    }

    @Test
    void testDeleteClue_Failure() {
  
        Clue clue = mock(Clue.class);
        when(clueDaoImplementation.deleteClue(clue)).thenReturn(false);

  
        boolean result = clueService.deleteClue(clue);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).deleteClue(clue);
    }

    @Test
    void testDeleteClue_WithNullClue() {
  
        when(clueDaoImplementation.deleteClue(null)).thenReturn(false);

  
        boolean result = clueService.deleteClue(null);

  
        assertFalse(result);
        verify(clueDaoImplementation, times(1)).deleteClue(null);
    }

    @Test
    void testConstructor_WithValidDao() {
  
        ClueService service = new ClueService(clueDaoImplementation);

  
        assertNotNull(service);
    }

    @Test
    void testCreateClue_WithParameters_VerifyClueCreation() {
  
        Name name = mock(Name.class);
        ClueText text = mock(ClueText.class);
        ClueTheme theme = mock(ClueTheme.class);
        Price price = mock(Price.class);

  
        clueService.createClue(name, text, theme, price);

  
        verify(clueDaoImplementation, times(1)).save(any(Clue.class));
    }

    @Test
    void testValidateExistClueInDataBase_MultipleCallsWithSameName() {
  
        Name name = mock(Name.class);
        Clue clue = mock(Clue.class);
        when(clueDaoImplementation.getClueByName(name)).thenReturn(clue);

  
        boolean result1 = clueService.validateExistClueInDataBase(name);
        boolean result2 = clueService.validateExistClueInDataBase(name);

  
        assertTrue(result1);
        assertTrue(result2);
        verify(clueDaoImplementation, times(2)).getClueByName(name);
    }

    ClueDaoImplementation clueDao = new ClueDaoImplementation();
    ClueService clueService2 = new ClueService(clueDao);

    @Test
    @DisplayName("Validate if a Clue exist by passing the ClueName")
    void testValidateExistClueInDataBase() {
        assertFalse((clueService2.validateExistClueInDataBase(new Name("patata"))));

        List <Clue> clueList =  clueService2.getAllClues();

        assertTrue(clueService2.validateExistClueInDataBase( clueList.getFirst().getName()));

    }
}