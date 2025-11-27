package player.dao;

import player.model.Player;

import java.util.List;
import java.util.Optional;

public class PlayerDaoImpl implements PlayerDao {
    @Override
    public void save(Player entity) {

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
}
