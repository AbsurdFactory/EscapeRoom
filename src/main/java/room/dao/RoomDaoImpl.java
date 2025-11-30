package room.dao;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;
import room.model.DifficultyLevel;
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

    private static final String DELETE_BY_NAME_SQL = """
            DELETE FROM room
            WHERE name = ?
            """;

    private static final String SELECT_ID_BY_NAME_SQL = """
        SELECT id_room
        FROM room
        WHERE name = ?
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

            ps.setString(1, object.getName().toString());
            ps.setBigDecimal(2, object.getPrice().toBigDecimal());
            ps.setString(3, object.getDifficultyLevel().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting room", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public Optional<Room> findById(Id id) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id.getValue());
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

            ps.setString(1, object.getName().toString());
            ps.setBigDecimal(2, object.getPrice().toBigDecimal());
            ps.setString(3, object.getDifficultyLevel().toString());
            ps.setInt(4, object.getId().getValue());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating room", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public boolean delete(Id id) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id.getValue());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting room with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public boolean deleteByName(Name name) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_BY_NAME_SQL)) {

            ps.setString(1, name.toString());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting room with name: " + name, e);
        } finally {
            dbConnection.closeConnection();
        }
    }



    private Room mapRow(ResultSet rs) throws SQLException {
        try {
            return new Room(
                    new Name(rs.getString("name")),
                    new Price(rs.getBigDecimal("price")),
                    new DifficultyLevel(rs.getString("difficulty_level"))
            );
        } catch (IllegalArgumentException e) {
            throw new SQLException("Error mapping room data: " + e.getMessage(), e);
        }
    }

    public Optional<Integer> getIdByName(String name) {
        dbConnection.openConnection();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_BY_NAME_SQL)) {

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id_room"));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DataAccessException("Error finding Room ID by name: " + name, e);
        } finally {
            dbConnection.closeConnection();
        }
    }
}
