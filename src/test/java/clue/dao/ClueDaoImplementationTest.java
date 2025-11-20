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

        Clue clue = new Clue("pista1","lalala","lolo",20.0);
        final String INSERT_SQL = """
        INSERT INTO clue 
        VALUES ("pista1","lalala","lolo",20.0)
        """;
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

            preparedStatement.setString(1, clue.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting escape room", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Test
    void deleteClue() {
    }

    @Test
    void getClueName() {
    }

    @Test
    void getClueTheme() {
    }

    @Test
    void setClueTheme() {
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