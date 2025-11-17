package databaseconnection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseConnectionTest {

    @Test
    @DisplayName("Testing if the connection have value")
    void getConnection() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        assertNotNull(databaseConnection.getConnection());

    }
}