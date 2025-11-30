package ticket.dao;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;
import player.dao.PlayerDaoImpl;
import player.service.PlayerService;
import room.dao.RoomDaoImpl;
import room.service.RoomService;
import ticket.model.Ticket;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class TicketDaoImpl implements TicketDao {
    private static final String INSERT_SQL = """
            INSERT INTO ticket (id_ticket, room_id_room, player_id_player, date_time, price)
            VALUES (?,?, ?, ?, ?, ?);
            """;
    private static final String INSERT_TICKET_SQL = """
            INSERT INTO ticket (player_id_player, room_id_room, date_time, price, room_price)
            VALUES (?, ?, ?, ?,?);
            """;
    private static final String SELECT_BY_ID = """
            SELECT id_ticket,room_id_room, player_id_player, date_time, price
            FROM ticket
            WHERE id_ticket = ?
            """;

    private static final String SELECT_TICKET_BY_ID = """
            SELECT id_ticket,room_id_room, player_id_player, date_time, price
            FROM ticket
            WHERE id_ticket = ?
            """;

    private static final String SELECT_ALL = """
            SELECT id_ticket, room_id_room, player_id_player, date_time, price
            FROM ticket
            """;
    private static final String UPDATE_SQL = """
            UPDATE ticket
            SET room_id_room = ?, player_id_player = ?, date_time = ?, price = ?
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
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_TICKET_SQL)) {
            PlayerDaoImpl playerDao = new PlayerDaoImpl();
            PlayerService playerService = new PlayerService(playerDao);
            Id idByNamePlayer = playerService.getIdByName(ticket.getPlayerName());

            RoomDaoImpl roomDao = new RoomDaoImpl();
            RoomService roomService = new RoomService(roomDao);
            Id idByNameRoom = roomService.getIdByName(ticket.getRoomName());

            preparedStatement.setInt(1, idByNamePlayer.getValue());
            preparedStatement.setInt(2, idByNameRoom.getValue());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(ticket.getDateTime()));
            preparedStatement.setBigDecimal(4, ticket.getPrice().toBigDecimal());
            preparedStatement.setBigDecimal(5, ticket.getPrice().toBigDecimal());

            preparedStatement.executeUpdate();

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

    public Ticket findTicketById(Id id) {
        dbConnection.openConnection();

        Ticket ticket = new Ticket();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id.getValue());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ticket = mapRow(rs);
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error finding ticket", e);
        } finally {
            dbConnection.closeConnection();
        }
        return ticket;
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
            ps.setTimestamp(3, Timestamp.valueOf(ticket.getDateTime()));
            ps.setBigDecimal(4, ticket.getPrice().toBigDecimal());
            ps.setInt(5, ticket.getId().getValue());

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
        }
    }

    @Override
    public boolean deleteByName(Name name) {
        return false;
    }


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

        return new Ticket(
                new Id(rs.getInt("id_ticket")),
                new Id(rs.getInt("room_id_room")),
                new Id(rs.getInt("player_id_player")),
                rs.getTimestamp("date_time").toLocalDateTime(),
                new Price(rs.getBigDecimal("price"))
        );
    }


}


