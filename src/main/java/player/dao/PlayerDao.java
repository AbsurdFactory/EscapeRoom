package player.dao;

import dao.BaseDao;
import player.model.Player;

import java.util.Optional;

public interface PlayerDao extends BaseDao<Player> {
    Optional<Player> findByEmail(String email);
    Optional<Player> findByNickName(String nickName);
}
