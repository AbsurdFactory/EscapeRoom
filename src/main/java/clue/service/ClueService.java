package clue.service;

import clue.dao.ClueDaoImplementation;
import clue.model.Clue;
import clue.model.ClueText;
import clue.model.ClueTheme;
import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;

import java.util.List;

public class ClueService {
    private final ClueDaoImplementation clueDaoImplementation;

    public ClueService(ClueDaoImplementation clueDao) {
        this.clueDaoImplementation = clueDao;
    }

    public void createClue(Name name, ClueText text, ClueTheme theme, Price price) {
        Clue clue = new Clue(name, text, theme, price);

        clueDaoImplementation.save(clue);
    }

    public void createClue(Clue clue) {
        clueDaoImplementation.save(clue);
    }

    public boolean validateExistClueInDataBase(Name name) {
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

    public boolean deleteClueByName(Name name) {

        return clueDaoImplementation.deleteByName(name);
    }

    public boolean deleteClue(Clue clue) {
        return clueDaoImplementation.deleteClue(clue);
    }

    public Id getIdClueByName(Name name) {
        return clueDaoImplementation.getIdClueByName(name);
    }
}
