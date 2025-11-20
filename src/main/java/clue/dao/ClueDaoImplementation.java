package clue.dao;

import clue.model.Clue;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

//TODO: this class must implement also the connectionDatabase interface
public class ClueDaoImplementation implements ClueDao {
    private final DatabaseConnection dbConnection;

    private static final String INSERT_SQL = """
        INSERT INTO clue (name)
        VALUES (?)
        """;


    public ClueDaoImplementation() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    @Override
    public void createClue(Clue clue) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, clue.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting escape room", e);
        } finally {
            dbConnection.closeConnection();
        }
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

    @Override
    public void save(ClueDao entity) {

    }

    @Override
    public Optional<ClueDao> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<ClueDao> findAll() {
        return List.of();
    }

    @Override
    public boolean update(ClueDao entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
