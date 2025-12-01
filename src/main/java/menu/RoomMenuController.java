package menu;

import clue.dao.ClueDaoImplementation;
import clue.service.ClueService;
import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.service.ObjectDecorationService;
import room.dao.RoomDaoImpl;
import room.model.DifficultyLevel;
import room.model.Room;
import room.model.RoomBuilder;
import room.service.RoomService;

import java.util.List;
import java.util.Scanner;

public class RoomMenuController extends BaseMenuController {

    private final RoomService roomService;
    private final ClueService clueService;
    private final ObjectDecorationService decorationService;

    public RoomMenuController(Scanner scanner) {
        super(scanner);

        this.roomService = new RoomService(new RoomDaoImpl());
        this.clueService = new ClueService(new ClueDaoImplementation());
        this.decorationService = new ObjectDecorationService(new ObjectDecorationDaoImpl());
    }

    @Override
    public void showMenu() {
        try {
            showRoomMenu();
        } catch (InputReadException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void showRoomMenu() throws InputReadException {
        int option;

        do {
            MenuPrinterNew.displayRoomMenu();
            System.out.print("Choose an option (1-8): ");

            option = askOption("", 1, 8);

            switch (option) {
                case 1 -> createRoom();
                case 2 -> showRoomDetails();
                case 3 -> listAllRooms();
                case 4 -> addClueToRoom();
                case 5 -> removeClueFromRoom();
                case 6 -> addDecorationToRoom();
                case 7 -> removeDecorationFromRoom();
                case 8 -> System.out.println("\nReturning to Main Menu...");
            }

        } while (option != 8);
    }


    private void createRoom() {
        System.out.println("\n--- Create a new Room ---");

        try {
            System.out.println("Please enter the name of the room.");
            Name name = ConsoleInputReader.readName(scanner, "room name");
            System.out.println("Please enter the difficulty level.");
            DifficultyLevel difficultyLevel =
                    ConsoleInputReader.readDifficultyLevel(scanner, "difficulty level");
            System.out.println("Please enter the price of the room.");
            Price price = ConsoleInputReader.readPrice(scanner, "room price");

            Room room = new RoomBuilder()
                    .withName(name)
                    .withDifficultyLevel(difficultyLevel)
                    .withPrice(price)
                    .build();

            roomService.createRoom(room);
            System.out.println("\nRoom created successfully!");

        } catch (Exception e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }


    private void showRoomDetails() throws InputReadException {
        Room room = findRoomByName();

        if (room == null) return;

        System.out.println("""
                
                === ROOM DETAILS ===
                Name: %s
                Difficulty: %s
                Price: %s
                =====================
                """.formatted(
                room.getName(),
                room.getDifficultyLevel(),
                room.getPrice()
        ));
    }


    private void listAllRooms() {
        List<Room> rooms = roomService.getAllRooms();

        if (rooms == null || rooms.isEmpty()) {
            System.out.println("There are no Rooms created yet.");
            return;
        }

        System.out.println("\n--- Rooms Available ---");
        for (Room r : rooms) {
            System.out.printf(" - %s (%s, %s)%n",
                    r.getName(),
                    r.getDifficultyLevel(),
                    r.getPrice());
        }
    }


    private void addClueToRoom() throws InputReadException {
        Room room = findRoomByName();
        if (room == null) return;

        System.out.println("Please enter the name of the clue.");
        Name clueName = ConsoleInputReader.readName(scanner, "clue name");

        try {
            roomService.addClueToRoomByName(room.getName(), clueName);
            System.out.printf("Clue '%s' added to Room '%s'.%n",
                    clueName, room.getName());
        } catch (Exception e) {
            System.out.println("ERROR adding Clue: " + e.getMessage());
        }
    }


    private void removeClueFromRoom() throws InputReadException {
        Room room = findRoomByName();
        if (room == null) return;

        System.out.println("Please enter the name of the clue.");
        Name clueName = ConsoleInputReader.readName(scanner, "clue name");

        try {
            roomService.removeClueFromRoomByName(room.getName(), clueName);
            System.out.printf("Clue '%s' removed from Room '%s'.%n",
                    clueName, room.getName());
        } catch (Exception e) {
            System.out.println("ERROR removing Clue: " + e.getMessage());
        }
    }


    private void addDecorationToRoom() throws InputReadException {
        Room room = findRoomByName();
        if (room == null) return;

        System.out.println("Please enter the name of the decoration.");
        Name decorationName = ConsoleInputReader.readName(scanner, "decoration name");

        try {
            roomService.addDecorationToRoomByName(room.getName(), decorationName);
            System.out.printf("Decoration '%s' added to Room '%s'.%n",
                    decorationName, room.getName());
        } catch (Exception e) {
            System.out.println("ERROR adding Decoration: " + e.getMessage());
        }
    }


    private void removeDecorationFromRoom() throws InputReadException {
        Room room = findRoomByName();
        if (room == null) return;

        System.out.println("Please enter the name of the decoration.");
        Name decorationName = ConsoleInputReader.readName(scanner, "decoration name");

        try {
            roomService.removeDecorationFromRoomByName(room.getName(), decorationName);
            System.out.printf("Decoration '%s' removed from Room '%s'.%n",
                    decorationName, room.getName());
        } catch (Exception e) {
            System.out.println("ERROR removing Decoration: " + e.getMessage());
        }
    }


    private Room findRoomByName() throws InputReadException {
        List<Room> rooms = roomService.getAllRooms();

        if (rooms == null || rooms.isEmpty()) {
            System.out.println("There are no Rooms created yet.");
            return null;
        }

        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            System.out.println(" - " + r.getName());
        }

        Room selectedRoom = null;

        while (selectedRoom == null) {
            System.out.println("Please enter the room name.");
            Name roomName = ConsoleInputReader.readName(scanner, "room name");

            for (Room r : rooms) {
                if (r.getName().toString().equalsIgnoreCase(roomName.toString())) {
                    selectedRoom = r;
                    break;
                }
            }

            if (selectedRoom == null) {
                System.out.println("No Room found. Try again.");
            }
        }

        return selectedRoom;
    }
}
