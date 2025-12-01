package menu;

import player.dao.PlayerDaoImpl;
import player.model.Player;
import player.service.PlayerService;
import player.service.SubscriptionResult;
import room.model.RoomEventPublisher;

import java.util.List;
import java.util.Scanner;

public class PlayerMenuController extends BaseMenuController {
    private final PlayerService playerService;

    public PlayerMenuController(Scanner scanner) {
        super(scanner);
        PlayerDaoImpl playerDao = new PlayerDaoImpl();
        RoomEventPublisher publisher = new RoomEventPublisher();
        this.playerService = new PlayerService(playerDao);
    }

    public void showPlayerMenu() throws InputReadException {
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

    public void handleSubscribePlayer() {
        try {
            System.out.print("Enter player nickname to subscribe: ");
            String nickname = ConsoleInputReader.readString(scanner, "Nickname");
            SubscriptionResult result = playerService.subscribePlayer(nickname);
            System.out.println((result.isSuccess() ? "Subscribed: " : "Not subscribed: ") + result.getMessage());
        } catch (InputReadException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error subscribing player: " + e.getMessage());
        }
    }

    public void handleNotifyPlayers() {
        try {
            System.out.print("Enter message to notify subscribed players: ");
            String message = ConsoleInputReader.readString(scanner, "Notification message");
            playerService.notifyPlayers(message);
            System.out.println("Notifications sent successfully.");
        } catch (InputReadException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error notifying players: " + e.getMessage());
        }
    }
}
