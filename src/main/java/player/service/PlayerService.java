package player.service;

import exceptions.BusinessException;
import exceptions.NotFoundException;
import player.dao.PlayerDao;
import player.model.Player;

import java.util.List;

public class PlayerService {
    private final PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public void createPlayer(String nickName, String email) {
        Player player = Player.create(nickName, email);

        if (playerDao.findByNickName(nickName).isPresent()) {
            throw new BusinessException("NickName already taken: " + nickName);
        }

        if (playerDao.findByEmail(email).isPresent()) {
            throw new BusinessException("Email already registered: " + email);
        }

        playerDao.save(player);
    }

    public Player getPlayerById(int id) {
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

    public boolean updatePlayer(int id, String newNickName, String newEmail) {
        Player existing = getPlayerById(id);
        Player updated = Player.rehydrate(id, newNickName, newEmail);
        return playerDao.update(updated);
    }

    public boolean deletePlayer(int id) {
        getPlayerById(id);
        return playerDao.delete(id);
    }
}
