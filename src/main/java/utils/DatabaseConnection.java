package utils;

import java.sql.Connection;

public interface DatabaseConnection {
    void openConnection();

    Connection getConnection();

    void closeConnection();
}
