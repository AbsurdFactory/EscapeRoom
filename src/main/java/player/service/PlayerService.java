package player.service;

import commonValueObjects.Id;
import exceptions.BusinessException;
import exceptions.NotFoundException;
import player.dao.PlayerDao;
import player.model.Player;

import java.util.List;

public class PlayerService {
    private final PlayerDaoImpl playerDao;

    public PlayerService(PlayerDaoImpl playerDao) {
        this.playerDao = playerDao;
    }

    public void createPlayer(String nickName, String email, boolean subscribed) {
        Player player = Player.create(nickName, email, subscribed);

        if (playerDao.findByNickName(nickName).isPresent()) {
            throw new BusinessException("NickName already taken: " + nickName);
        }

        if (playerDao.findByEmail(email).isPresent()) {
            throw new BusinessException("Email already registered: " + email);
        }

        playerDao.save(player);
    }

    public Player getPlayerById(Id id) {
        return playerDao.findById(id)
                .orElseThrow(() -> new NotFoundException("Player not found with ID: " + id));
    }

    public Player getPlayerByNickName(String nickName) {
        return playerDao.findByNickName(nickName)
                .orElseThrow(() -> new NotFoundException("Player not found with nickname: " + nickName));
    }

    public Player getPlayerByEmail(String email) {
        return playerDao.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Player not found with email: " + email));
    }

    public List<Player> getAllPlayers() {
        return playerDao.findAll();
    }

    public boolean updatePlayer(Id id, String newNickName, String newEmail, boolean subscribed) {
        Player existing = getPlayerById(id);
        Player updated = Player.rehydrate(id.getValue(), newNickName, newEmail, subscribed);
        return playerDao.update(updated);
    }

    public boolean deletePlayer(Id id) {
        getPlayerById(id);
        return playerDao.delete(id);
    }

    public Id getIdByName(Name name) {
        return playerDao.getIdByName(name.toString())
                .map(Id::new)
                .orElseThrow(() -> new NotFoundException("Player not found with name: " + name));
    }
}
