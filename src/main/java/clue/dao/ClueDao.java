package clue.dao;

import clue.model.Clue;
import dao.BaseDao;
import escaperoom.model.EscapeRoom;

import java.util.List;

public interface ClueDao extends BaseDao<ClueDao>  {

    void createClue(Clue clue);

    void deleteClue(Clue clue);

    String getClueName(Clue clue);

    String getClueTheme(Clue clue);

    void setClueTheme(Clue clue, String theme);

    void setClueAction(Clue clue, String action);

    String getClueAction(Clue clue);

    List<Clue> getAllClues();
}
