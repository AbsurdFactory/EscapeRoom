package escaperoom.dao;

import commonValueObjects.Id;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import escaperoom.model.EscapeRoom;
import exceptions.DataAccessException;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EscapeRoomDaoImpl implements EscapeRoomDao {
    private static final String INSERT_SQL = """
        INSERT INTO escape (name)
        VALUES (?)
        """;

    private static final String SELECT_BY_ID = """
        SELECT id_escape, name
        FROM escape
        WHERE id_escape = ?
        """;

    private static final String SELECT_BY_NAME = """
    SELECT id_escape, name
    FROM escape
    WHERE name = ?
    """;

    private static final String SELECT_ALL = """
        SELECT id_escape, name
        FROM escape
        """;

    private static final String UPDATE_SQL = """
        UPDATE escape
        SET name = ?
        WHERE id_escape = ?
        """;

    private static final String DELETE_SQL = """
        DELETE FROM escape
        WHERE id_escape = ?
        """;


    private final DatabaseConnection dbConnection;
    public EscapeRoomDaoImpl() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    public List<EscapeRoom> findByName(String name) {
        dbConnection.openConnection();
        List<EscapeRoom> results = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_NAME)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                results.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error finding escape room with name: " + name, e);
        } finally {
            dbConnection.closeConnection();
        }

        return results;
    }

    @Override
    public void save(EscapeRoom object) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, object.getName().toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting escape room", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public Optional<EscapeRoom> findById(Id id) {
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
            throw new DataAccessException("Error finding escape room with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }    }

    public Optional<EscapeRoom> findById(int id) {
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
            throw new DataAccessException("Error finding escape room with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public List<EscapeRoom> findAll() {
        dbConnection.openConnection();
        List<EscapeRoom> escapeRooms = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                escapeRooms.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving escape rooms", e);
        } finally {
            dbConnection.closeConnection();
        }

        return escapeRooms;
    }

    @Override
    public boolean update(EscapeRoom object) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, object.getName().toString());
            ps.setInt(4, object.getId().getValue());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating escape room", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public boolean delete(Id id) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id.hashCode());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting escape room with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public boolean delete(int id) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting escape room with ID: " + id, e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private EscapeRoom mapRow(ResultSet rs) throws SQLException {
        return new EscapeRoom(
                rs.getInt("id_escape"),
                rs.getString("name")
        );
    }

}
