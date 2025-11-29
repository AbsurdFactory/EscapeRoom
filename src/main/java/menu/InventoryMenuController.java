package menu;

import clue.dao.ClueDao;
import clue.dao.ClueDaoImplementation;
import clue.service.ClueService;
import commonValueObjects.Name;
import exceptions.ValidationException;
import inventory.InventoryService;
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

    public InventoryMenuController(Scanner scanner) {
        super(scanner);
        this.inventoryService = new InventoryService();
        this.clueService = new ClueService(clueDaoImplementation);
        this.roomService = new RoomService(roomDao);
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
                default:
                    // No debería llegar aquí por la validación de readOption
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

        printAllRooms();

        try {
            System.out.println("Please enter the name of the room.");
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
        // TODO: integrar con ClueService/inventoryService para eliminar una pista
        System.out.println("Removing a Clue from inventory (not implemented yet).");
    }

    private void removeDecorationFromInventory() {
        // TODO: integrar con servicio de decoraciones/inventario
        System.out.println("Removing a Decoration from inventory (not implemented yet).");
    }

    private void printAllRooms() {
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

    @Override
    public void showMenu() throws InputReadException {
        showRemoveFromInventoryMenu();
    }


}
