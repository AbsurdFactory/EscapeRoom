package menu;


import java.util.Scanner;

public class MainMenuController extends BaseMenuController {
    private final EscapeRoomMenuController escapeRoomMenuController;
    private final InventoryMenuController inventoryMenuController;
    private final RoomMenuController roomMenuController;
    private final ClueMenuController clueMenuController;
    private final ObjectDecorationMenuController decorationMenuController;
    private final PlayerMenuController playerMenuController;
    private final TicketMenuControler ticketMenuController;


    public MainMenuController() {
        this(new Scanner(System.in));
    }

     public MainMenuController(Scanner scanner) {
        super(scanner);
        this.escapeRoomMenuController = new EscapeRoomMenuController(scanner);
        this.inventoryMenuController = new InventoryMenuController(scanner);
        this.roomMenuController = new RoomMenuController(scanner);
        this.clueMenuController = new ClueMenuController(scanner);
        this.decorationMenuController = new ObjectDecorationMenuController(scanner);
        this.playerMenuController = new PlayerMenuController(scanner);
        this.ticketMenuController = new TicketMenuControler(scanner);
    }

      public void start() {
        int mainOption;
          try {
              do {
                  MenuPrinter.displayMainMenu();
                  mainOption = readMainOption();
                  handleMainMenu(mainOption);
              } while (mainOption != 0);

              System.out.println("\nExiting application...");
          } catch (Exception e) {
              System.out.println("\nCritical error: " + e.getMessage());
              System.out.println("The application will close.");
          }
    }

       private int readMainOption() {
        int option = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Choose an option (0-11): ");
            try {
                option = ConsoleInputReader.readInt(scanner);

                if (option < 0 || option > 11) {
                    System.out.println("\nInvalid option. Please choose an option between 0 and 11.\n");
                } else {
                    valid = true;
                }
            } catch (InputReadException e) {
                System.out.println("\nERROR: " + e.getMessage() + "\n");
            }
        }

        return option;
    }

    private void handleMainMenu(int option) throws InputReadException {
        switch (option) {
            case 1:
                handleCreateEscapeRoom();
                break;
            case 2:
                handleAddRoom();
                break;
            case 3:
                handleAddClue();
                break;
            case 4:
                handleAddDecoration();
                break;
            case 5:
                handleViewInventory();
                break;
            case 6:
                handleRemoveFromInventory();
                break;
            case 7:
                handleCreatePlayer();
                break;
            case 8:
                handleGenerateTicket();
                break;
            case 9:
                handleCalculateTotalIncome();
                break;
            case 10:
                handleNotifyPlayers();
                break;
            case 11:
                handleSubscribePlayer();
                break;
            case 0:
                break;
        }
    }


    private void handleCreateEscapeRoom() throws InputReadException {
        System.out.println("\n--- Create a new Escape Room ---");
        escapeRoomMenuController.showEscapeRoomMenu();
    }

    private void handleAddRoom() throws InputReadException {
        System.out.println("\n--- Add a new Room ---");
        roomMenuController.showRoomMenu();
    }

    private void handleAddClue() throws InputReadException {
        System.out.println("\n--- Add a new Clue ---");
        clueMenuController.showClueMenu();
    }

    private void handleAddDecoration() throws InputReadException {
        System.out.println("\n--- Add a new Decoration ---");
        decorationMenuController.showDecorationMenu();
    }

    private void handleViewInventory() throws InputReadException {
        System.out.println("\n--- View Inventory ---");
        inventoryMenuController.showInventoryMenu();
    }

    private void handleRemoveFromInventory() throws InputReadException {
        System.out.println("\n--- Remove from Inventory ---");
        inventoryMenuController.showRemoveFromInventoryMenu();

    }

    private void handleCreatePlayer() throws InputReadException {
        System.out.println("\n--- Create a new Player ---");
        playerMenuController.showPlayerMenu();
    }

    private void handleGenerateTicket() throws InputReadException {
        System.out.println("\n--- Generate ticket for a Player ---");
        ticketMenuController.showTicketMenu();
    }

    private void handleCalculateTotalIncome() {
        System.out.println("\n--- Calculate total income from tickets ---");
        System.out.println("This feature is not fully implemented yet.");
    }

    private void handleNotifyPlayers() {
        System.out.println("\n--- Notify players about events ---");
        playerMenuController.handleNotifyPlayers();
    }

    private void handleSubscribePlayer() {
        System.out.println("\n--- Subscribe a new player to notifications ---");
        playerMenuController.handleSubscribePlayer();
    }

    @Override
    public void showMenu() throws InputReadException {
        start();
    }
}

