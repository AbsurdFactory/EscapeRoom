package clue.dao;

import clue.model.Clue;

import java.util.List;

public interface ClueDao {
    Clue createClue();
    void deleteClue();
    String getClueName();
    String getClueTheme();
    void setClueTheme();
    void setClueAction();
    String getClueAction();
    List<Clue> getAllClues();

}
