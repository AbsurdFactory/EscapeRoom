package databaseconnection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseConnectionTest {

    @Test
    @DisplayName("Testing if the connection have value")
    void getConnection() {
        MYSQLDatabaseConnection databaseConnection ;
        try {
            databaseConnection = new MYSQLDatabaseConnection();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(databaseConnection.getConnection());

    }
}