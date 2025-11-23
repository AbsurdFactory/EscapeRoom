package clue.dao;

import clue.model.Clue;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: this class must implement also the connectionDatabase interface
public class ClueDaoImplementation implements ClueDao {

    private static final String INSERT_CLUE_SQL = """
            INSERT INTO clue (name)
            VALUES (?)
            """;

    private static final String DELETE_CLUE_BY_NAME_SQL = """
               DELETE FROM clue WHERE name = ?
            """;

    private static final String SELECT_CLUE_BY_NAME_SQL = """
               SELECT * FROM clue WHERE name = ?
            """;

    private static final String UPDATE_CLUE_BY_NAME = """
               UPDATE clue SET text = ?, theme = ?, price = ?  WHERE name = ?
            """;


    private static final String UPDATE_CLUE_BY_ID = """
               UPDATE clue SET name=?, text = ?, theme = ?, price = ? WHERE name = ?
            """;

    private static final String SELECT_ALL_CLUE_SQL = """
               SELECT * FROM clue
            """;

    private final DatabaseConnection dbConnection;

    public ClueDaoImplementation() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }


    public void createClue(Clue clue) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CLUE_SQL)) {
            preparedStatement.setString(1, clue.getName());
            preparedStatement.setString(2, clue.getText());
            preparedStatement.setString(3, clue.getTheme());
            preparedStatement.setDouble(4, clue.getPrice());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public void deleteClue(Clue clue) {
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLUE_BY_NAME_SQL)) {

            preparedStatement.setString(1, clue.getName());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public String getClueByName(Clue clue) {
        dbConnection.openConnection();
        String name = "";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLUE_BY_NAME_SQL)) {
            preparedStatement.setString(1, clue.getName());

            ResultSet resultSet =preparedStatement.executeQuery();

            while (resultSet.next()){
                name = resultSet.getString("name");
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error getting clue name", e);
        } finally {
            dbConnection.closeConnection();
        }
        return name;
    }


    public String getClueTheme(Clue clue) {
        //Find clue theme
        //"SELECT theme FROM escaperoom.clue WHERE name = " + clue.name
        return "";
    }


    public void setClueTheme(Clue clue, String theme) {
        //UPDATE theme into clue table
        //"UPDATE escaperoom.clue SET theme = " + theme + " WHERE name = " + clue.name
    }

    public void setClueAction(Clue clue, String action) {
        //UPDATE text into clue table
        //"UPDATE escaperoom.clue SET text = " + text + " WHERE name = " + clue.name
    }

    public String getClueAction(Clue clue) {
        //Find clue action
        //"SELECT text FROM escaperoom.clue WHERE name = " + clue.name
        return "";
    }


    @Override
    public List<Clue> findAll() {
        dbConnection.openConnection();
        List<Clue> clueList = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLUE_SQL)) {
            ResultSet resultSet =preparedStatement.executeQuery();

            while (resultSet.next()){
                clueList.add(ClueMapRow(resultSet));                }

              } catch (SQLException e) {
            throw new DataAccessException("Error getting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
        return List.copyOf(clueList);
    }


    private Clue ClueMapRow(ResultSet resultSet) {
        try {
            return new Clue(
                    resultSet.getString("name"),
                    resultSet.getString("text"),
                    resultSet.getString("theme"),
                    resultSet.getDouble("price")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void save(Clue entity) {

    }

    @Override
    public Optional<Clue> findById(int id) {
        return Optional.empty();
    }


    @Override
    public boolean update(Clue entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
