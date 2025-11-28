package objectdecoration.dao;

import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import objectdecoration.model.ObjectDecoration;
import exceptions.DataAccessException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectDecorationDaoImpl implements ObjectDecorationDao {

    private static final String INSERT_SQL = """
        INSERT INTO decoration_object (name, material, price)
        VALUES (?, ?, ?)
        """;

    private static final String SELECT_BY_ID = """
        SELECT id_decoration_object, name, material, price
        FROM decoration_object
        WHERE id_decoration_object = ?
        """;

    private static final String SELECT_ALL = """
        SELECT id_decoration_object, name, material, price
        FROM decoration_object
        """;

    private static final String UPDATE_SQL = """
        UPDATE decoration_object
        SET name = ?, material = ?, price = ?
        WHERE id_decoration_object = ?
        """;

    private static final String DELETE_SQL = """
        DELETE FROM decoration_object
        WHERE id_decoration_object = ?
        """;
    private final DatabaseConnection dbConnection;
    public ObjectDecorationDaoImpl() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }
    @Override
    public void save(ObjectDecoration object) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, object.getName().toString());
            ps.setString(2, object.getMaterial().getValue());
            ps.setBigDecimal(3, object.getPrice().toBigDecimal());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting decoration", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public Optional<ObjectDecoration> findById(int id) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DataAccessException("Error finding decoration with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public List<ObjectDecoration> findAll() {
        dbConnection.openConnection();
        List<ObjectDecoration> decorations = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                decorations.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving decorations", e);
        } finally {
            dbConnection.closeConnection();
        }

        return decorations;
    }

    @Override
    public boolean update(ObjectDecoration object) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, object.getName().toString());
            ps.setString(2, object.getMaterial().getValue());
            ps.setBigDecimal(3, object.getPrice().toBigDecimal());
            ps.setInt(4, object.getId().getValue());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating decoration", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public boolean delete(int id) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting decoration with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private ObjectDecoration mapRow(ResultSet rs) throws SQLException {
        return ObjectDecoration.rehydrate(
                rs.getInt("id_object"),
                rs.getString("name"),
                rs.getString("material"),
                rs.getDouble("price")
        );
    }
}
