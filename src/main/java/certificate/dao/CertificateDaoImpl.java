package certificate.dao;

import certificate.model.Certificate;
import certificate.model.CertificateRewardType;
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

public class CertificateDaoImpl implements CertificateDao {

    private static final String INSERT_SQL = """
            INSERT INTO certificate (text_body, reward, player_id_player, room_id_room)
            VALUES (?, ?, ?, ?)
            """;

    private static final String SELECT_BY_ID = """
            SELECT id_certificate, text_body, reward, player_id_player, room_id_room
            FROM certificate
            WHERE id_certificate = ?
            """;

    private static final String SELECT_ALL = """
            SELECT id_certificate, text_body, reward, player_id_player, room_id_room
            FROM certificate
            """;

    private static final String UPDATE_SQL = """
            UPDATE certificate
            SET text_body = ?, reward = ?, player_id_player = ?, room_id_room = ?
            WHERE id_certificate = ?
            """;

    private static final String DELETE_SQL = """
            DELETE FROM certificate
            WHERE id_certificate = ?
            """;

    private final DatabaseConnection dbConnection;

    public CertificateDaoImpl() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    @Override
    public void save(Certificate certificate) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, certificate.getTextBody());
            ps.setString(2, certificate.getReward().name());
            ps.setInt(3, certificate.getPlayerId());
            ps.setInt(4, certificate.getRoomId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting certificate", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public Optional<Certificate> findById(int id) {
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
            throw new DataAccessException("Error finding certificate", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public List<Certificate> findAll() {
        dbConnection.openConnection();
        List<Certificate> certificates = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                certificates.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving certificates", e);
        } finally {
            dbConnection.closeConnection();
        }

        return certificates;
    }

    @Override
    public boolean update(Certificate certificate) {
        dbConnection.openConnection();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, certificate.getTextBody());
            ps.setString(2, certificate.getReward().name());
            ps.setInt(3, certificate.getPlayerId());
            ps.setInt(4, certificate.getRoomId());
            ps.setInt(5, certificate.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DataAccessException("Error updating certificate", e);
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
            throw new DataAccessException("Error deleting certificate", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    private Certificate mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_certificate");
        String textBody = rs.getString("text_body");
        CertificateRewardType reward =
                CertificateRewardType.valueOf(rs.getString("reward"));

        int playerId = rs.getInt("player_id_player");
        int roomId = rs.getInt("room_id_room");

        return new Certificate(id, reward, textBody, playerId, roomId);
    }
}
