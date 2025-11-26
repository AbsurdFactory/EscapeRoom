package clue.dao;

import clue.model.*;
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

    private static final String GET_TOTAL_CLUE_PRICE = """
            SELECT SUM(price) AS totalPrice FROM clue
            """;

    private static final String GET_TOTAL_CLUE_UNITS = """
            SELECT COUNT(*) AS totalClues FROM clue
            """;


    private final DatabaseConnection dbConnection;

    public ClueDaoImplementation() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    public boolean deleteClue(Clue clue) {
        dbConnection.openConnection();
        boolean succes = false;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLUE_BY_NAME_SQL)) {

            preparedStatement.setString(1, clue.getName().toString());

            preparedStatement.execute();
            succes = true;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
        return succes;
    }

    public boolean deleteClueByName(ClueName name) {
        dbConnection.openConnection();
        boolean succes = false;
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLUE_BY_NAME_SQL)) {

            preparedStatement.setString(1, name.toString());

            preparedStatement.execute();
            succes = true;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting Clue", e);
        } finally {
            dbConnection.closeConnection();
        }
        return succes;
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

    public Clue getClueByName(ClueName name) {
        Clue clue1 = null;
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLUE_BY_NAME_SQL)) {

            preparedStatement.setString(1, name.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                clue1 = getCLue(resultSet);
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
        return List.copyOf(themeList);
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
                    new ClueName(resultSet.getString("name")),
                    new ClueText(resultSet.getString("text")),
                    new ClueTheme(resultSet.getString("theme")),
                    new CluePrice(resultSet.getDouble("price"))
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
            preparedStatement.setString(1, clue.getName().toString());
            preparedStatement.setString(2, clue.getText().toString());
            preparedStatement.setString(3, clue.getTheme().toString());
            preparedStatement.setDouble(4, clue.getPrice().toDouble());

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

            preparedStatement.setString(1, clue.getText().toString());
            preparedStatement.setString(2, clue.getTheme().toString());
            preparedStatement.setDouble(3, clue.getPrice().toDouble());
            preparedStatement.setString(4, clue.getName().toString());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating clue", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private int getIdClueByCLue(Clue clue) {
        int id_clue = 0;
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_CLUE_BY_NAME_SQL)) {
            preparedStatement.setString(1, clue.getName().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                id_clue = resultSet.getInt("id_clue");
            }
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
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            id_clue = resultSet.getInt("id_clue");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            dbConnection.closeConnection();
        }
        return id_clue;
    }


    public double getTotalCluePrice() {
        double totalPrice = 0;
        dbConnection.openConnection();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_CLUE_PRICE)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalPrice = resultSet.getDouble("totalPrice");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            dbConnection.closeConnection();
        }

        return totalPrice;
    }

    public double getTotalCluesUnits() {
        int totalUnits;
        dbConnection.openConnection();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_CLUE_UNITS)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            totalUnits = resultSet.getInt("totalCLues");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            dbConnection.closeConnection();
        }

        return totalUnits;
    }


    @Override
    public boolean delete(int id) {
        return false;
    }
}
