package menu;

public class MenuPrinterNew {

    private static final String MAIN_MENU = """
            
            =============================
                    MAIN MENU
            =============================
             1. Create Escape Room
             2. Add Room
             3. Add Clue
             4. Add Decoration
             5. View Inventory
             6. Remove from Inventory
             7. Create Player
             8. Generate Ticket
             9. Calculate Total Income
            10. Notify Players
            11. Subscribe Player
             0. Exit
            =============================
            """;

    private static final String INVENTORY_MENU = """
            
            ====================================
                   INVENTORY INFORMATION
            ====================================
             1. Show total number of items
             2. Show total Rooms
             3. Show total Clues
             4. Show total Decorations
             5. Show total value of Inventory
             6. Back to Main Menu
            ====================================
            """;

    private static final String REMOVE_INVENTORY_MENU = """
            
            ====================================
                  REMOVE FROM INVENTORY
            ====================================
             1. Remove Room
             2. Remove Clue
             3. Remove Decoration
             4. Back to Inventory Menu
            ====================================
            """;

    private static final String ESCAPE_ROOM_MENU = """
            
            ====================================
                  ESCAPE ROOM CREATION
            ====================================
             1. Create Escape Room
             2. Add Room to Escape Room
             3. Show Escape Room details
             4. Back to Main Menu
            ====================================
            """;

    private static final String ROOM_MENU = """
            
            ====================================
                        ROOM MENU
            ====================================
             1. Create new Room
             2. List Rooms
             3. Back
            ====================================
            """;

    private static final String CLUE_MENU = """
            
            ====================================
                        CLUE MENU
            ====================================
             1. Create new Clue
             2. List Clues
             3. Back
            ====================================
            """;

    private static final String DECORATION_MENU = """
            
            ====================================
                     DECORATION MENU
            ====================================
             1. Create Decoration
             2. List Decorations
             3. Back
            ====================================
            """;

    private static final String PLAYER_MENU = """
            
            ====================================
                        PLAYER MENU
            ====================================
             1. Create Player
             2. List Players
             3. Back
            ====================================
            """;

    private static void print(String menu) {
        System.out.println(menu);
    }

    public static void displayMainMenu() {
        print(MAIN_MENU);
    }

    public static void displayShowInventorySubmenu() {
        print(INVENTORY_MENU);
    }

    public static void displayRemoveFromInventorySubmenu() {
        print(REMOVE_INVENTORY_MENU);
    }

    public static void displayEscapeRoomCreationMenu() {
        print(ESCAPE_ROOM_MENU);
    }

    public static void displayClueMenu() {
        print(CLUE_MENU);
    }

    public static void displayDecorationMenu() {
        print(DECORATION_MENU);
    }

    public static void displayPlayerMenu() {
        print(PLAYER_MENU);
    }

    public static void displayRoomMenu() {
        System.out.println("\n--- Room Menu ---");
        System.out.println("1. Create Room");
        System.out.println("2. Show Room Details");
        System.out.println("3. List All Rooms");
        System.out.println("4. Add Clue to Room");
        System.out.println("5. Remove Clue from Room");
        System.out.println("6. Add Decoration to Room");
        System.out.println("7. Remove Decoration from Room");
        System.out.println("8. Return to Main Menu");
    }
}
