package menu;

import clue.dao.ClueDao;
import clue.dao.ClueDaoImplementation;
import clue.model.Clue;
import clue.service.ClueService;
import commonValueObjects.Name;
import exceptions.ValidationException;
import inventory.InventoryService;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.ObjectDecoration;
import objectdecoration.service.ObjectDecorationService;
import room.dao.RoomDao;
import room.dao.RoomDaoImpl;
import room.model.Room;
import room.service.RoomService;

import java.util.Scanner;

public class InventoryMenuController extends BaseMenuController {
    private final InventoryService inventoryService;
    private final ClueDaoImplementation clueDaoImplementation = new ClueDaoImplementation();
    private final ClueService clueService;
    private final RoomDao roomDao = new RoomDaoImpl();
    private final RoomService roomService;
    private final ObjectDecorationDao objectDecorationDao = new ObjectDecorationDaoImpl();
    private final ObjectDecorationService objectDecorationService;

    public InventoryMenuController(Scanner scanner) {
        super(scanner);
        this.inventoryService = new InventoryService();
        this.clueService = new ClueService(clueDaoImplementation);
        this.roomService = new RoomService(roomDao);
        this.objectDecorationService = new ObjectDecorationService(objectDecorationDao);
    }

    public void showInventoryMenu() {
        int subOption;

        do {
            MenuPrinter.displayShowInventorySubmenu();
            subOption = askOption("Choose an option (1-6): ", 1, 6);

            switch (subOption) {
                case 1:
                    showTotalItems();
                    break;
                case 2:
                    showTotalRooms();
                    break;
                case 3:
                    showTotalClues();
                    break;
                case 4:
                    showTotalDecorations();
                    break;
                case 5:
                    showTotalInventoryValue();
                    break;
                case 6:
                    System.out.println("\nReturning to the Main Menu...");
                    break;
            }

        } while (subOption != 6);
    }


    public void showRemoveFromInventoryMenu() {
        int subOption;

        do {
            MenuPrinter.displayRemoveFromInventorySubmenu();
            subOption = askOption("Choose an option (1-4): ", 1, 4);

            switch (subOption) {
                case 1:
                    removeRoomFromInventory();
                    break;
                case 2:
                    removeClueFromInventory();
                    break;
                case 3:
                    removeDecorationFromInventory();
                    break;
                case 4:
                    System.out.println("\nReturning to the Inventory Menu...");
                    break;
            }

        } while (subOption != 4);
    }


    private void showTotalItems() {
        System.out.println("Total number of items in the inventory: " +
                inventoryService.getAllItemsQuantitiesFromInventory());
    }

    private void showTotalRooms() {
        System.out.println("Total number of rooms in the inventory: " +
                inventoryService.getAllRoomsFromInventory());
    }

    private void showTotalClues() {
        System.out.println("Total number of clues in the inventory: " +
                inventoryService.getAllCluesFromInventory());
    }

    private void showTotalDecorations() {
        System.out.println("Total number of decorations in the inventory: " +
                inventoryService.getAllObjectDecorationsFromInventory());
    }

    private void showTotalInventoryValue() {
        System.out.println("Total value of the inventory: " +
                inventoryService.getTotalValueOfInventory());
    }

    private void removeRoomFromInventory() {
        System.out.println("\n=== Remove Room From Inventory ===");

        getAllRooms();

        try {
            System.out.println("Please enter the name of the room you want to remove.");
            String roomNameRaw = ConsoleInputReader.readNonEmptyString(scanner, "room name");
            Name roomName = new Name(roomNameRaw);

            boolean removed = roomService.deleteRoomByName(roomName);

            if (removed) {
                System.out.println("Room '" + roomName + "' has been removed from the system.");
            } else {
                System.out.println("No room found with name: " + roomName);
            }

        } catch (InputReadException | ValidationException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println();
    }


    private void removeClueFromInventory() {
        System.out.println("\n=== Remove Clue From Inventory ===");

        getAllClues();

        try {
            System.out.println("Please enter the name of the clue you want to remove.");
            Name clueName = ConsoleInputReader.readName(scanner, "clue name");

            boolean removed = clueService.deleteClueByName(clueName);

            if (removed) {
                System.out.println("Clue '" + clueName + "' has been removed from the system.");
            } else {
                System.out.println("No clue found with name: " + clueName);
            }

        } catch (InputReadException | ValidationException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println();
    }


    private void removeDecorationFromInventory() {
        System.out.println("\n=== Remove Decoration From Inventory ===");

        getAllDecorations();

        try {
            System.out.println("Please enter the name of decoration you want to remove.");
            Name decorationName = ConsoleInputReader.readName(scanner, "decoration name");

            boolean removed = objectDecorationService.deleteObjectDecorationByName(decorationName);

            if (removed) {
                System.out.println("Decoration '" + decorationName + "' has been removed from the system.");
            } else {
                System.out.println("No decoration found with name: " + decorationName);
            }

        } catch (InputReadException | ValidationException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println();
    }


    private void getAllRooms() {
        System.out.println("\n=== Rooms in inventory ===");

        var rooms = roomService.getAllRooms(); // o como tengas llamado el método

        if (rooms.isEmpty()) {
            System.out.println("There are no rooms in the inventory.");
            return;
        }

        for (Room room : rooms) {
            System.out.println(
                    "Name: " + room.getName() +
                            " | Price: " + room.getPrice().toBigDecimal() +
                            " | Difficulty: " + room.getDifficultyLevel()
            );
        }

        System.out.println();

    }

    private void getAllClues() {
        System.out.println("\n=== Clues in inventory ===");

        try {
            var clues = clueService.getAllClues();

            if (clues.isEmpty()) {
                System.out.println("There are no clues in the inventory.");
                return;
            }

            for (Clue clue : clues) {
                System.out.println("Name: " + clue.getName());
            }

        } catch (Exception e) {
            System.out.println("ERROR retrieving clues: " + e.getMessage());
        }

        System.out.println();
    }

    private void getAllDecorations() {
        System.out.println("\n=== Decorations in inventory ===");

        try {
            var decorations = objectDecorationService.getAll();

            if (decorations.isEmpty()) {
                System.out.println("There are no decorations in the inventory.");
                return;
            }

            for (ObjectDecoration decoration : decorations) { // o ObjectDecoration si se llama así
                System.out.println("Name: " + decoration.getName());
            }

        } catch (Exception e) {
            System.out.println("ERROR retrieving decorations: " + e.getMessage());
        }

        System.out.println();
    }

    @Override
    public void showMenu() throws InputReadException {
        showRemoveFromInventoryMenu();
    }


}
