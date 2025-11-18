package room.dao;

import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;
import room.model.Room;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {

    private static final String INSERT_SQL = """
            INSERT INTO room (name, price, difficultyLevel)
            VALUES (?, ?, ?)
            """;

    private static final String SELECT_BY_ID = """
        SELECT id_room, name, price, difficulty_level
        FROM room
        WHERE id_room = ?
        """;

    private static final String SELECT_ALL = """
        SELECT id_room, name, price, difficulty_level
        FROM room
        """;

    private static final String UPDATE_SQL = """
        UPDATE room
        SET name = ?, price = ?, difficulty_level = ?
        WHERE id_room = ?
        """;

    private static final String DELETE_SQL = """
        DELETE FROM room
        WHERE id_room = ?
        """;

    private final DatabaseConnection dbConnection;
    public RoomDaoImpl() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    @Override
    public void save(Room object) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, object.getName());
            ps.setDouble(2, object.getPrice());
            ps.setInt(3, object.getDifficultyLevel());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting room", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public Optional<Room> findById(int id) {
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
            throw new DataAccessException("Error finding room with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public List<Room> findAll() {
        dbConnection.openConnection();
        List<Room> rooms = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                rooms.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving rooms", e);
        } finally {
            dbConnection.closeConnection();
        }

        return rooms;
    }

    @Override
    public boolean update(Room object) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, object.getName());
            ps.setDouble(2, object.getPrice());
            ps.setInt(3, object.getDifficultyLevel());
            ps.setInt(4, object.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating room", e);
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
            throw new DataAccessException("Error deleting room with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private Room mapRow(ResultSet rs) throws SQLException {
        return new Room(
                rs.getInt("id_decoration_object"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("difficulty_level")
        );
    }


}
