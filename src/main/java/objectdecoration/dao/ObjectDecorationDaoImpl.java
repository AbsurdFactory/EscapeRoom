package objectdecoration.dao;

import objectdecoration.model.ObjectDecoration;
import exceptions.DataAccessException;
import utils.DatabaseConnection;

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

    @Override
    public void save(ObjectDecoration object) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, object.getName());
            ps.setString(2, object.getMaterial());
            ps.setDouble(3, object.getPrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting decoration", e);
        }
    }

    @Override
    public Optional<ObjectDecoration> findById(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error finding decoration with ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<ObjectDecoration> findAll() {
        List<ObjectDecoration> decorations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                decorations.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all decorations", e);
        }

        return decorations;
    }

    @Override
    public boolean update(ObjectDecoration object) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, object.getName());
            ps.setString(2, object.getMaterial());
            ps.setDouble(3, object.getPrice());
            ps.setInt(4, object.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating decoration with ID: " + object.getId(), e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting decoration with ID: " + id, e);
        }
    }

    private ObjectDecoration mapRow(ResultSet rs) throws SQLException {
        return new ObjectDecoration(
                rs.getInt("id_decoration_object"),
                rs.getString("name"),
                rs.getString("material"),
                rs.getDouble("price")
        );
    }
}
