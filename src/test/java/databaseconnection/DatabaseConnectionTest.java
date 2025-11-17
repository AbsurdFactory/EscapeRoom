package databaseconnection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.MYSQLDatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseConnectionTest {

    @Test
    @DisplayName("Testing if the connection have value")
    void testGetConnection() throws SQLException, IOException, ClassNotFoundException {
        MYSQLDatabaseConnection databaseConnection;
        databaseConnection = new MYSQLDatabaseConnection();

        databaseConnection.openConnection();

        assertNotNull(databaseConnection.getConnection());

    }

    @Test
    @DisplayName("Testing if the connection is closed")
    void testCloseConnection() throws SQLException, IOException, ClassNotFoundException {
        MYSQLDatabaseConnection databaseConnection;
        databaseConnection = new MYSQLDatabaseConnection();

        databaseConnection.openConnection();
        databaseConnection.closeConnection();

        Assertions.assertTrue(databaseConnection.getConnection().isClosed());


    }
}