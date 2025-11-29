package menu;

public class MenuPrinter {

    //TODO: convertir todo a una constante
    public static void displayMainMenu() {
        System.out.println("\n=============================");
        System.out.println("        MAIN MENU");
        System.out.println("=============================");
        System.out.println(" 1. Create Escape Room");
        System.out.println(" 2. Add Room");
        System.out.println(" 3. Add Clue");
        System.out.println(" 4. Add Decoration");
        System.out.println(" 5. View Inventory");
        System.out.println(" 6. Remove from Inventory");
        System.out.println(" 7. Create Player");
        System.out.println(" 8. Generate Ticket");
        System.out.println(" 9. Calculate Total Income");
        System.out.println("10. Notify Players");
        System.out.println("11. Subscribe Player");
        System.out.println(" 0. Exit");
        System.out.println("=============================");
    }


    public static void displayShowInventorySubmenu() {
        System.out.println("\n====================================");
        System.out.println("        INVENTORY INFORMATION");
        System.out.println("====================================");
        System.out.println(" 1. Show total number of items");
        System.out.println(" 2. Show total Rooms");
        System.out.println(" 3. Show total Clues");
        System.out.println(" 4. Show total Decorations");
        System.out.println(" 5. Show total value of Inventory");
        System.out.println(" 6. Back to Main Menu");
        System.out.println("====================================");
    }


    public static void displayRemoveFromInventorySubmenu() {
        System.out.println("\n====================================");
        System.out.println("      REMOVE FROM INVENTORY");
        System.out.println("====================================");
        System.out.println(" 1. Remove Room");
        System.out.println(" 2. Remove Clue");
        System.out.println(" 3. Remove Decoration");
        System.out.println(" 4. Back to Inventory Menu");
        System.out.println("====================================");
    }


    public static void displayEscapeRoomCreationMenu() {
        System.out.println("\n====================================");
        System.out.println("       ESCAPE ROOM CREATION");
        System.out.println("====================================");
        System.out.println(" 1. Create Escape Room");
        System.out.println(" 2. Add Room to Escape Room");
        System.out.println(" 3. Show Escape Room details");
        System.out.println(" 4. Back to Main Menu");
        System.out.println("====================================");
    }


    public static void displayRoomMenu() {
        System.out.println("\n====================================");
        System.out.println("             ROOM MENU");
        System.out.println("====================================");
        System.out.println(" 1. Create new Room");
        System.out.println(" 2. List Rooms");
        System.out.println(" 3. Back");
        System.out.println("====================================");
    }


    public static void displayClueMenu() {
        System.out.println("\n====================================");
        System.out.println("             CLUE MENU");
        System.out.println("====================================");
        System.out.println(" 1. Create new Clue");
        System.out.println(" 2. List Clues");
        System.out.println(" 3. Back");
        System.out.println("====================================");
    }

    public static void displayDecorationMenu() {
        System.out.println("\n====================================");
        System.out.println("         DECORATION MENU");
        System.out.println("====================================");
        System.out.println(" 1. Create Decoration");
        System.out.println(" 2. List Decorations");
        System.out.println(" 3. Back");
        System.out.println("====================================");
    }


    public static void displayPlayerMenu() {
        System.out.println("\n====================================");
        System.out.println("           PLAYER MENU");
        System.out.println("====================================");
        System.out.println(" 1. Create Player");
        System.out.println(" 2. List Players");
        System.out.println(" 3. Back");
        System.out.println("====================================");
    }
}
