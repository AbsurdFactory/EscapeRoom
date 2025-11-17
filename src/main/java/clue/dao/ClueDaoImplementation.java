package clue.dao;

import clue.model.Clue;

import java.util.List;
//TODO: this class must implement also the connectionDatabase interface
public class ClueDaoImplementation implements ClueDao {

    @Override
    public void createClue(Clue clue) {
        //insert clue into clue table
        //"INSERT INTO escaperoom.clue values("+ clue.name + "," + clue.text +"," + clue.theme + "," + clue.price)"
    }

    @Override
    public void deleteClue(Clue clue) {
        //delete clue from clue table
        //"DELETE IF EXIST escaperoom.clue where name = " + clue.name"
    }

    @Override
    public String getClueName(Clue clue) {
        //Find clue name
        //"SELECT name FROM escaperoom.clue WHERE ?"
        return "";
    }

    @Override
    public String getClueTheme(Clue clue) {
        //Find clue theme
        //"SELECT theme FROM escaperoom.clue WHERE name = " + clue.name
        return "";
    }

    @Override
    public void setClueTheme(Clue clue, String theme) {
        //UPDATE theme into clue table
        //"UPDATE escaperoom.clue SET theme = " + theme + " WHERE name = " + clue.name
    }

    @Override
    public void setClueAction(Clue clue, String action) {
        //UPDATE text into clue table
        //"UPDATE escaperoom.clue SET text = " + text + " WHERE name = " + clue.name
    }

    @Override
    public String getClueAction(Clue clue) {
        //Find clue action
        //"SELECT text FROM escaperoom.clue WHERE name = " + clue.name
        return "";
    }

    @Override
    public List<Clue> getAllClues() {
        //Find clue action
        //"SELECT * FROM escaperoom.clue "
        //foreach record -> new clue(name,text,theme,price)
        return List.of();
    }
}
