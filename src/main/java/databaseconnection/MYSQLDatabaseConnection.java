package databaseconnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MYSQLDatabaseConnection implements DatabaseConnection{

    private static volatile MYSQLDatabaseConnection instance;
    private final Connection connection;
    private String urlDatabase;
    private String databaseUser;
    private String passwordDatabase;

    public MYSQLDatabaseConnection() throws SQLException, ClassNotFoundException, IOException {
        getDatabaseProperties();

        this.connection = DriverManager.getConnection(urlDatabase, databaseUser, passwordDatabase);
    }

    public static MYSQLDatabaseConnection getInstance() throws SQLException, ClassNotFoundException, IOException {
        if (instance == null) {
            synchronized (MYSQLDatabaseConnection.class) {
                if (instance == null) {
                    instance = new MYSQLDatabaseConnection();
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getDatabaseProperties() throws IOException {
        Properties databaseProperties = new Properties();

        databaseProperties.load(new FileInputStream("src/main/java/properties/escaperoom_db.properties"));
        urlDatabase = databaseProperties.getProperty("url");
        databaseUser = databaseProperties.getProperty("user");
        passwordDatabase = databaseProperties.getProperty("password");

    }
}