package clue.dao;

import clue.model.Clue;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



class ClueDaoImplementationTest {
    private  DatabaseConnection dbConnection;
    @BeforeEach
    void setUp() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    @Test
    void createClue() {

        Clue clue = new Clue("pista4","fsdfsdf","lalala",20.0);
        final String INSERT_SQL = """
           INSERT INTO clue (name,text,price)
           VALUES (?,?,?)
        """;

        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, clue.getName());
            preparedStatement.setString(2, clue.getText());
          //  preparedStatement.setString(3, clue.getTheme());
            preparedStatement.setDouble(3, clue.getPrice());
            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Test
    void deleteClue() {
        Clue clue = new Clue("pista1","lalala","lolo",20.0);
        final String DELETE_SQL = """
           DELETE FROM clue WHERE name = ?
        """;

        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

            preparedStatement.setString(1, clue.getName());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Test
    void getClueName() {
        Clue clue = new Clue("pista1","lalala","lolo",20.0);
        final String SELECT_SQL = """
           SELECT * FROM clue WHERE name = ?
        """;

        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {

            preparedStatement.setString(1, clue.getName());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error getting clue name", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Test
    void getClueTheme() {
        Clue clue = new Clue("pista1","lalala","lolo",20.0);
        final String SELECT_SQL = """
           SELECT * FROM clue WHERE theme = ?
        """;

        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {

            preparedStatement.setString(1, clue.getTheme());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error getting clue theme", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Test
    void setClueThemeByName() {
        Clue clue = new Clue("pista3","lalala","lolo",20.0);
        final String SELECT_SQL = """
           UPDATE clue SET theme = ? WHERE name = ?
        """;

        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {

            preparedStatement.setString(1, clue.getTheme());
            preparedStatement.setString(2, clue.getName());


            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error getting clue theme", e);
        } finally {
            dbConnection.closeConnection();
        }

    }

    @Test
    void setClueAction() {
    }

    @Test
    void getClueAction() {
    }

    @Test
    void getAllClues() {
    }

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}