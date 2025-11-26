package clue.service;

import clue.dao.ClueDaoImplementation;
import clue.model.Clue;

import java.util.List;

public class ClueService {
    private final ClueDaoImplementation clueDaoImplementation;

    public ClueService(ClueDaoImplementation clueDao) {
        this.clueDaoImplementation = clueDao;
    }

    public void createClue(String name, String text, String theme, Double price) {
        Clue clue = new Clue(name, text,theme,price);

        clueDaoImplementation.save(clue);
    }

    public void createClue(Clue clue) {
        clueDaoImplementation.save(clue);
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

    public boolean deleteClueByName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Invalid name");
        }
        return clueDaoImplementation.deleteClueByName(name);
    }

    public boolean deleteClue(Clue clue){
      return  clueDaoImplementation.deleteClue(clue);
    }
}
