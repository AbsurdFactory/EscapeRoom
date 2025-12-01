package player.service;

import commonValueObjects.Id;
import commonValueObjects.Name;
import exceptions.BusinessException;
import exceptions.NotFoundException;
import player.dao.PlayerDao;
import player.dao.PlayerDaoImpl;
import player.model.Player;
import room.model.RoomEventPublisher;

import java.util.List;

public class PlayerService {
    private final PlayerDaoImpl playerDao;
    private final RoomEventPublisher publisher;

    public PlayerService(PlayerDaoImpl playerDao) {
        this.playerDao = playerDao;
        this.publisher = new RoomEventPublisher();
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

    public Player getPlayerById(Id<Player> id) {
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

    public boolean updatePlayer(Id<Player> id, String newNickName, String newEmail, boolean subscribed) {
        Player existing = getPlayerById(id);
        Player updated = Player.rehydrate(id.getValue(), newNickName, newEmail, subscribed);
        return playerDao.update(updated);
    }

    public boolean deletePlayer(Id<Player> id) {
        getPlayerById(id);
        return playerDao.delete(id);
    }

    public SubscriptionResult subscribePlayer(String nickName) {
        Player player = playerDao.findByNickName(nickName)
                .orElseThrow(() -> new NotFoundException("Player not found: " + nickName));

        if (player.isSubscribed()) {
            return new SubscriptionResult(false, "Player " + nickName + " is already subscribed.");
        }

        Player updated = Player.rehydrate(
                player.getId().getValue(),
                player.getNickName().getValue(),
                player.getEmail().getValue(),
                true
        );
        playerDao.update(updated);
        publisher.subscribe(updated);

        return new SubscriptionResult(true, "Player " + nickName + " subscribed successfully.");
    }

    public void notifyPlayers(String message) {
        List<Player> allPlayers = playerDao.findAll();
        allPlayers.stream()
                .filter(Player::isSubscribed)
                .forEach(publisher::subscribe);

        publisher.notifySubscribers(message);
    }

    public Id getIdByName (Name name){
        return playerDao.getIdByName(name.toString())
                .map(Id::new)
                .orElseThrow(() -> new NotFoundException("Player not found with name: " + name));
    }


}

