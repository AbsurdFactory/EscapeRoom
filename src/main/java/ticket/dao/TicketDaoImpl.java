package ticket.dao;

import commonValueObjects.Id;
import dao.BaseDao;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;
import ticket.model.Ticket;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TicketDaoImpl implements TicketDao {
    private static final String INSERT_SQL = """
            INSERT INTO ticket (id_ticket, room_id_room, player_id_player, date, time, price)
            VALUES (?,?, ?, ?, ?, ?);
            """;
    private static final String SELECT_BY_ID = """
            SELECT id_ticket,room_id_room, player_id_player, date, time, price
            FROM ticket
            WHERE id_ticket = ?
            """;
    private static final String SELECT_ALL = """
            SELECT id_ticket, room_id_room; player_id_player, date, time, price
            FROM ticket
            """;
    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET room_id_room = ?, player_id_player = ?, date = ?, time = ?, price = ?
            WHERE id_ticket=?
            """;
    private static final String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id_ticket = ?
            """;

    private final DatabaseConnection dbConnection;

    public TicketDaoImpl() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    @Override
    public void save(Ticket ticket) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setInt(1, ticket.getId().getValue());
            ps.setInt(2, ticket.getRoomId().getValue());
            ps.setInt(3, ticket.getPlayerId().getValue());
            ps.setDate(4, Date.valueOf(ticket.getDate()));
            ps.setTime(5, Time.valueOf(ticket.getTime()));
            ps.setBigDecimal(6, ticket.getPrice());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting Ticket", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public Optional<Ticket> findById(Id id) {
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
            throw new DataAccessException("Error finding ticket", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    public Optional<Ticket> findById(int id) {
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
            throw new DataAccessException("Error finding ticket", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public List<Ticket> findAll() {
        dbConnection.openConnection();
        List<Ticket> tickets = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tickets.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error listing tickets", e);
        } finally {
            dbConnection.closeConnection();
        }

        return tickets;
    }

    @Override
    public boolean update(Ticket ticket) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setInt(1, ticket.getRoomId().getValue());
            ps.setInt(2, ticket.getPlayerId().getValue());
            ps.setDate(3, Date.valueOf(ticket.getDate()));
            ps.setTime(4, Time.valueOf(ticket.getTime()));
            ps.setBigDecimal(5, ticket.getPrice());
            ps.setInt(6, ticket.getId().getValue());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating ticket", e);
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
            throw new DataAccessException("Error deleting ticket", e);
        } finally {
            dbConnection.closeConnection();
        }    }


    public boolean delete(int id) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error deleting ticket", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private Ticket mapRow(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();
        rs.getInt("id_ticket");
        rs.getString("id_room");
        rs.getString("id_player");
        rs.getDate("date").toLocalDate();
        rs.getTime("time").toLocalTime();
        rs.getDouble("price");
        return ticket;
    }

}


