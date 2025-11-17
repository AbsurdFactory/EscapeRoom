package databaseconnection;

import java.sql.Connection;

public interface DatabaseConnection {
    Connection getConnection();
    void closeConnection();
}
