package player.service;

import exceptions.BusinessException;
import exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.dao.PlayerDao;
import player.model.Player;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlayerServiceTest {

    private PlayerDao playerDao;
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        playerDao = mock(PlayerDao.class);
        playerService = new PlayerService(playerDao);
    }

    @Test
    void shouldRegisterPlayerSuccessfully() {
        when(playerDao.findByNickName("Nick")).thenReturn(Optional.empty());
        when(playerDao.findByEmail("nick@mail.com")).thenReturn(Optional.empty());

        playerService.createPlayer("Nick", "nick@mail.com");

        verify(playerDao, times(1)).save(any(Player.class));
    }

    @Test
    void shouldThrowExceptionWhenNicknameExists() {
        when(playerDao.findByNickName("Nick")).thenReturn(Optional.of(Player.create("Nick", "nick@mail.com")));

        assertThrows(BusinessException.class, () -> playerService.createPlayer("Nick", "nick@mail.com"));
    }

    @Test
    void shouldThrowNotFoundWhenPlayerDoesNotExist() {
        when(playerDao.findById(99)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> playerService.getPlayerById(99));
    }

    @Test
    void shouldReturnAllPlayers() {
        when(playerDao.findAll()).thenReturn(List.of(Player.create("Nick", "nick@mail.com")));

        List<Player> players = playerService.getAllPlayers();

        assertEquals(1, players.size());
        verify(playerDao, times(1)).findAll();
    }
}
