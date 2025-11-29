package menu;

import clue.dao.ClueDao;
import clue.dao.ClueDaoImplementation;
import clue.service.ClueService;
import inventory.InventoryService;

import java.util.Scanner;

public class InventoryMenuController extends BaseMenuController {
    private final InventoryService inventoryService;
    private final ClueDaoImplementation clueDaoImplementation = new ClueDaoImplementation();
    private final ClueService clueService;

    public InventoryMenuController(Scanner scanner) {
        super(scanner);
        this.inventoryService = new InventoryService();
        this.clueService = new ClueService(clueDaoImplementation);
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
        // TODO: pedir datos necesarios (id, nombre, etc.) y llamar al servicio
        System.out.println("Removing a Room from inventory (not implemented yet).");
        // int roomId = askRoomId();
        // inventoryService.removeRoomFromInventory(roomId);
    }

    private void removeClueFromInventory() {
        // TODO: integrar con ClueService/inventoryService para eliminar una pista
        System.out.println("Removing a Clue from inventory (not implemented yet).");
    }

    private void removeDecorationFromInventory() {
        // TODO: integrar con servicio de decoraciones/inventario
        System.out.println("Removing a Decoration from inventory (not implemented yet).");
    }


    @Override
    public void showMenu() throws InputReadException {
        showRemoveFromInventoryMenu();
    }
}
