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

public class ClueDaoImplementation implements ClueDao {

    private static final String INSERT_CLUE_SQL = """
            INSERT INTO clue (name,text,theme,price)
            VALUES (?,?,?,?)
            """;

    private static final String DELETE_CLUE_BY_NAME_SQL = """
               DELETE FROM clue WHERE name = ?
            """;

    private static final String SELECT_CLUE_BY_NAME_SQL = """
               SELECT * FROM clue WHERE name = ?
            """;

    private static final String SELECT_ID_CLUE_BY_NAME_SQL = """
               SELECT id FROM clue WHERE name = ?
            """;

    private static final String SELECT_ALL_THEMES = """
               SELECT theme FROM clue
            """;

    private static final String SELECT_ALL_CLUE_SQL = """
               SELECT * FROM clue
            """;


    private static final String UPDATE_CLUE_BY_NAME_SQL = """
            UPDATE clue
            SET text = ?, theme = ?, price = ?
                  WHERE name = ?;
            """;

    private static final String DELETE_CLUE_BY_ID_SQL = """
            DELETE clue WHERE id_clue = ?
            """;

    private final DatabaseConnection dbConnection;

    public ClueDaoImplementation() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
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

    public void deleteClueByName(String name) {
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLUE_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }


    public void deleteClueById(int id) {
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLUE_BY_ID_SQL)) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public Clue getClueByName(String name) {
        Clue clue1 = null;
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLUE_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clue1 =  getCLue(resultSet);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            dbConnection.closeConnection();
        }
        return clue1;
    }

    public List<String> getAllCluesThemes() {

        List<String> themeList = new ArrayList<>();
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_THEMES)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                themeList.add(resultSet.getString("theme"));
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            dbConnection.closeConnection();
        }
        return themeList;
    }

    @Override
    public List<Clue> findAll() {
        dbConnection.openConnection();
        List<Clue> clueList = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLUE_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clueList.add(getCLue(resultSet));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error getting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
        return List.copyOf(clueList);
    }


    private Clue getCLue(ResultSet resultSet) {
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

    private int getClueId(ResultSet resultSet) {
        int id_clue;
        try {
            id_clue = resultSet.getInt("id_clue");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_clue;
    }


    @Override
    public void save(Clue clue) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CLUE_SQL)) {
            preparedStatement.setString(1, clue.getName());
            preparedStatement.setString(2, clue.getText());
            preparedStatement.setString(3, clue.getTheme());
            preparedStatement.setDouble(4, clue.getPrice());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        } finally {
            dbConnection.closeConnection();
        }

    }

    @Override
    public Optional<Clue> findById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean update(Clue clue) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CLUE_BY_NAME_SQL)) {

            preparedStatement.setString(1, clue.getText());
            preparedStatement.setString(2, clue.getTheme());
            preparedStatement.setDouble(3, clue.getPrice());
            preparedStatement.setString(4, clue.getName());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private int getIdClueByCLue(Clue clue) {
        int id_clue;
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_CLUE_BY_NAME_SQL)) {
            preparedStatement.setString(1, clue.getName());
            ResultSet resultSet = preparedStatement.executeQuery();

            id_clue = resultSet.getInt("id_clue");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            dbConnection.closeConnection();
        }
        return id_clue;
    }

    private int getIdClueByName(String name) {
        int id_clue;
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_CLUE_BY_NAME_SQL)) {
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();

            id_clue = resultSet.getInt("id_clue");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            dbConnection.closeConnection();
        }
        return id_clue;
    }


    @Override
    public boolean delete(int id) {
        return false;
    }
}
