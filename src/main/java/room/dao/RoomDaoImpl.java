package room.dao;

import clue.model.Clue;
import clue.model.ClueText;
import clue.model.ClueTheme;
import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;
import objectdecoration.model.ObjectDecoration;
import room.model.DifficultyLevel;
import room.model.Room;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {

    private static final String INSERT_SQL = """
            INSERT INTO room (name, price, difficulty_level)
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

    private static final String SELECT_CLUES_BY_ROOM_ID = """
    SELECT c.id_clue, c.name, c.text, c.theme, c.price
    FROM clue c
    INNER JOIN room_has_clues rhc ON c.id_clue = rhc.clue_id_clue
    WHERE rhc.room_id_room = ?
    """;

    private static final String SELECT_DECORATIONS_BY_ROOM_ID = """
    SELECT d.id_decoration_object, d.name, d.description, d.material, d.price
    FROM decoration_object d
    INNER JOIN room_has_decoration_object rhd ON d.id_decoration_object = rhd.decoration_object_id_decoration_object
    WHERE rhd.room_id_room = ?
    """;

    private static final String COUNT_CLUES_BY_ROOM_ID = """
    SELECT COUNT(*) as total
    FROM room_has_clues
    WHERE room_id_room = ?
    """;

    private static final String COUNT_DECORATIONS_BY_ROOM_ID = """
    SELECT COUNT(*) as total
    FROM room_has_decoration_object
    WHERE room_id_room = ?
    """;

    private static final String SUM_CLUES_PRICE_BY_ROOM_ID = """
    SELECT COALESCE(SUM(c.price), 0) as total
    FROM clue c
    INNER JOIN room_has_clues rhc ON c.id_clue = rhc.clue_id_clue
    WHERE rhc.room_id_room = ?
    """;

    private static final String SUM_DECORATIONS_PRICE_BY_ROOM_ID = """
    SELECT COALESCE(SUM(d.price), 0) as total
    FROM decoration_object d
    INNER JOIN room_has_decoration_object rhd ON d.id_decoration_object = rhd.decoration_object_id_decoration_object
    WHERE rhd.room_id_room = ?
    """;

    private static final String SELECT_NAME_BY_ID = """
    SELECT name
    FROM room
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

    public List<Clue> getCluesByRoomId(Id roomId) {
        dbConnection.openConnection();
        List<Clue> clues = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_CLUES_BY_ROOM_ID)) {

            ps.setInt(1, roomId.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    clues.add(mapClueRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error getting clues for room ID: " + roomId, e);
        } finally {
            dbConnection.closeConnection();
        }

        return clues;
    }

    public List<ObjectDecoration> getDecorationsByRoomId(Id roomId) {
        dbConnection.openConnection();
        List<ObjectDecoration> decorations = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_DECORATIONS_BY_ROOM_ID)) {

            ps.setInt(1, roomId.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    decorations.add(mapDecorationRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error getting decorations for room ID: " + roomId, e);
        } finally {
            dbConnection.closeConnection();
        }

        return decorations;
    }

    public int countCluesByRoomId(Id roomId) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_CLUES_BY_ROOM_ID)) {

            ps.setInt(1, roomId.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error counting clues for room ID: " + roomId, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public int countDecorationsByRoomId(Id roomId) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_DECORATIONS_BY_ROOM_ID)) {

            ps.setInt(1, roomId.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
                return 0;
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error counting decorations for room ID: " + roomId, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public Price sumCluesPriceByRoomId(Id roomId) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SUM_CLUES_PRICE_BY_ROOM_ID)) {

            ps.setInt(1, roomId.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Price(rs.getBigDecimal("total"));
                }
                return new Price(BigDecimal.ZERO);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error summing clues price for room ID: " + roomId, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public Price sumDecorationsPriceByRoomId(Id roomId) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SUM_DECORATIONS_PRICE_BY_ROOM_ID)) {

            ps.setInt(1, roomId.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Price(rs.getBigDecimal("total"));
                }
                return new Price(BigDecimal.ZERO);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error summing decorations price for room ID: " + roomId, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private Clue mapClueRow(ResultSet rs) throws SQLException {
        try {
            return new Clue(
                    new Name(rs.getString("name")),
                    new ClueText(rs.getString("text")),
                    new ClueTheme(rs.getString("theme")),
                    new Price(rs.getBigDecimal("price"))
            );
        } catch (IllegalArgumentException e) {
            throw new SQLException("Error mapping clue data: " + e.getMessage(), e);
        }
    }

    private ObjectDecoration mapDecorationRow(ResultSet rs) throws SQLException {
        try {
            return ObjectDecoration.rehydrate(
                    rs.getInt("id_decoration_object"),
                    rs.getString("name"),
                    rs.getString("material"),
                    rs.getDouble("price")
            );
        } catch (IllegalArgumentException e) {
            throw new SQLException("Error mapping decoration data: " + e.getMessage(), e);
        }
    }

    public Optional<Name> getNameById(Id id) {
        dbConnection.openConnection();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_NAME_BY_ID)) {

            preparedStatement.setInt(1, id.getValue());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(new Name(resultSet.getString("name")));
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new DataAccessException("Error finding Room name by ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }
}
