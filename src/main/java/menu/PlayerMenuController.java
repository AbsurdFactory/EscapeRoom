package menu;

import player.dao.PlayerDao;
import player.dao.PlayerDaoImpl;
import player.model.Player;
import player.service.PlayerService;

import java.util.List;
import java.util.Scanner;

public class PlayerMenuController extends BaseMenuController {
    private final PlayerService playerService;
    private final PlayerDao playerDao = new PlayerDaoImpl();

    public PlayerMenuController(Scanner scanner) {
        super(scanner);
        this.playerService = new PlayerService(playerDao);
    }

    public void showPlayerMenu() {
        int option;

        do {
            MenuPrinter.displayPlayerMenu();
            option = askOption("Choose an option (1-3): ", 1, 3);

            switch (option) {
                case 1:
                    handleCreatePlayer();
                    break;
                case 2:
                    handleListPlayers();
                    break;
                case 3:
                    System.out.println("\nReturning to previous menu...");
                    break;
            }

        } while (option != 3);
    }


    private void handleCreatePlayer() {
        System.out.println("\n--- Create new Player ---");

        try {
            System.out.print("Please enter the player's nickname: ");
            String nickName = ConsoleInputReader.readNonEmptyString(scanner, "player nickname");

            System.out.print("Please enter the player's email: ");
            String email = ConsoleInputReader.readNonEmptyString(scanner, "player email");

            System.out.println("Is the player subscribed? (true/false, yes/no, y/n)");
            boolean subscribed = ConsoleInputReader.readBoolean(scanner, "subscribed");

            playerService.createPlayer(nickName, email, subscribed);

            System.out.println("\n✔ The player was successfully created.");

        } catch (InputReadException e) {
            System.out.println("\nERROR: " + e.getMessage());
            System.out.println("The player could not be created.");
        } catch (Exception e) {
            System.out.println("\nUnexpected error while creating the Player.");
        }
    }


    private void handleListPlayers() {
        System.out.println("\n--- List of Players ---");

        try {
            List<Player> players = playerService.getAllPlayers();

            if (players == null || players.isEmpty()) {
                System.out.println("There are no players registered yet.");
                return;
            }

            for (Player player : players) {
                System.out.println("------------------------------");

                System.out.println("Nickname: " + player.getNickName());
                System.out.println("Email: " + player.getEmail());
                System.out.println("Subscribed: " + player.isSubscribed());
            }
            System.out.println("------------------------------");

        } catch (Exception e) {
            System.out.println("\nERROR retrieving Players list.");
        }
    }

    @Override
    public void showMenu() throws InputReadException {
        showPlayerMenu();
    }
}
