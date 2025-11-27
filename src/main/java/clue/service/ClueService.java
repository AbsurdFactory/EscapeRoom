package clue.service;

import clue.dao.ClueDaoImplementation;
import clue.model.*;

import java.util.List;

public class ClueService {
    private final ClueDaoImplementation clueDaoImplementation;

    public ClueService(ClueDaoImplementation clueDao) {
        this.clueDaoImplementation = clueDao;
    }

    public void createClue(ClueName name, ClueText text, ClueTheme theme, CluePrice price) {
        Clue clue = new Clue(name, text, theme, price);

        clueDaoImplementation.save(clue);
    }

    public void createClue(Clue clue) {
        clueDaoImplementation.save(clue);
    }

    public boolean validateExistClueInDataBase(ClueName name) {
        return clueDaoImplementation.getClueByName(name) != null;
    }

    public List<Clue> getAllClues() {
        return clueDaoImplementation.findAll();
    }

    public List<String> getAllThemes() {
        return clueDaoImplementation.getAllCluesThemes();
    }

    public boolean updateClue(Clue clue) {
        return clueDaoImplementation.update(clue);
    }

    public boolean deleteClueByName(ClueName name) {

        return clueDaoImplementation.deleteClueByName(name);
    }

    public boolean deleteClue(Clue clue) {
        return clueDaoImplementation.deleteClue(clue);
    }
}
