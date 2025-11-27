package player.dao;

import databaseconnection.DatabaseConnection;
import databaseconnection.MYSQLDatabaseConnection;
import exceptions.DataAccessException;
import player.model.Player;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class PlayerDaoImpl implements PlayerDao {

    private static final String INSERT_SQL = """
        INSERT INTO player (nick_name, email) VALUES (?, ?)
        """;

    private static final String SELECT_BY_ID = """
        SELECT id_player, nick_name, email FROM player WHERE id_player = ?
        """;

    private final DatabaseConnection dbConnection;

    public PlayerDaoImpl() {
        try {
            this.dbConnection = MYSQLDatabaseConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new DataAccessException("Failed to initialize database connection", e);
        }
    }

    @Override
    public void save(Player entity) {
        dbConnection.openConnection();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, entity.getNickName().getValue());
            ps.setString(2, entity.getEmail().getValue());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error saving player", e);
        } finally {
            dbConnection.closeConnection();
        }
    }

    @Override
    public Optional<Player> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Player> findAll() {
        return null;
    }

    @Override
    public boolean update(Player entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        return Optional.empty();
    }

    private Player mapRow(ResultSet rs) throws SQLException {
        return Player.rehydrate(
                rs.getInt("id_player"),
                rs.getString("nick_name"),
                rs.getString("email")
        );
    }
}
