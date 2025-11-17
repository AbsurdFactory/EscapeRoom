package databaseconnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static volatile DatabaseConnection instance;
    private final Connection connection;
    private String urlDatabase;
    private String databaseUser;
    private String passwordDatabase;
    private String driverClassName;

    public DatabaseConnection() {
        getDatabaseProperties();
        try {
            Class.forName(driverClassName);
            this.connection = DriverManager.getConnection(urlDatabase, databaseUser, passwordDatabase);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void getDatabaseProperties() {
        Properties databaseProperties = new Properties();
        try {
            databaseProperties.load(new FileInputStream("src/main/java/properties/escaperoom_db.properties"));
            urlDatabase = databaseProperties.getProperty("url");
            databaseUser = databaseProperties.getProperty("user");
            passwordDatabase = databaseProperties.getProperty("password");
            driverClassName = databaseProperties.getProperty("driver-class-name");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}