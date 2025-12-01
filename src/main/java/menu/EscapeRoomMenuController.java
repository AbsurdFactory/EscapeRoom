package menu;

import commonValueObjects.Name;
import commonValueObjects.Price;
import escaperoom.dao.EscapeRoomDaoImpl;
import escaperoom.model.EscapeRoom;
import escaperoom.model.EscapeRoomBuilder;
import escaperoom.service.EscapeRoomService;
import room.dao.RoomDaoImpl;
import room.model.DifficultyLevel;
import room.model.Room;
import room.model.RoomBuilder;
import room.service.RoomService;
import java.util.List;
import java.util.Scanner;

public class EscapeRoomMenuController extends BaseMenuController {

    private final EscapeRoomService escapeRoomService;
    private final RoomService roomService;
    private final EscapeRoomDaoImpl escapeRoomDao = new EscapeRoomDaoImpl();
    private final RoomDaoImpl roomDao = new RoomDaoImpl();

    public EscapeRoomMenuController(Scanner scanner) {
        super(scanner);
        this.escapeRoomService = new EscapeRoomService(escapeRoomDao);
        this.roomService = new RoomService(roomDao);
    }


    public void showEscapeRoomMenu() throws InputReadException {
        int option;

        do {
            MenuPrinter.displayEscapeRoomCreationMenu();
            option = askOption("Choose an option (1-4): ", 1, 4);

            switch (option) {
                case 1:
                    createEscapeRoom();
                    break;
                case 2:
                    addRoomToEscapeRoom();
                    break;
                case 3:
                    showEscapeRoomDetails();
                    break;
                case 4:
                    System.out.println("\nReturning to Main Menu...");
                    break;
                default:
                    break;
            }

        } while (option != 4);
    }


    private void createEscapeRoom() {
        System.out.println("\n--- Create a new Escape Room ---");

        try {
            System.out.print("Enter the Escape Room name: ");
            Name name = ConsoleInputReader.readName(scanner, "Escape Room name");

            EscapeRoomBuilder builder = new EscapeRoomBuilder();

            EscapeRoom escapeRoom = builder
                    .withName(name)
                    .build();
            escapeRoomService.createEscapeRoom(escapeRoom);

            System.out.println("\nEscape Room created successfully!");

        } catch (InputReadException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected error while creating the Escape Room.");
        }
    }

    private void addRoomToEscapeRoom() throws InputReadException {
        System.out.println("\n--- Add Room to Escape Room ---");
        EscapeRoom selectedEscapeRoom = findByName();
        if (selectedEscapeRoom == null) {
            return;
        }

        RoomBuilder roomBuilder = new RoomBuilder();

        System.out.println("Please enter the name of the Room.");
        Name roomName = ConsoleInputReader.readName(scanner, "room name");
        System.out.println("Please enter the difficulty level (\"Easy\", \"Medium\", \"Hard\")");
        DifficultyLevel difficultyLevel = ConsoleInputReader.readDifficultyLevel(scanner, "difficulty level");
        System.out.println("Please enter the base price of the Room.");
        Price price = ConsoleInputReader.readPrice(scanner, "room price");

        Room room = roomBuilder
                .withName(roomName)
                .withDifficultyLevel(difficultyLevel)
                .withPrice(price)
                .build();

        roomService.createRoom(room);

        selectedEscapeRoom.addRoom(room);

        System.out.println("\nThe room was successfully created and added to Escape Room '" + selectedEscapeRoom.getName() + "'.");

    }


    private void showEscapeRoomDetails() throws InputReadException {
        System.out.println("\n--- Show Escape Room Details ---");
        EscapeRoom escapeRoom = findByName();

        if (escapeRoom == null) {
            return;
        }

        System.out.println("""
                
                === ESCAPE ROOM DETAILS ===
                Name: %s
                Rooms: %d
                =============================
                """.formatted(escapeRoom.getName(), escapeRoom.getRooms().size()));

    }

    @Override
    public void showMenu() throws InputReadException {
        showEscapeRoomMenu();
    }

    private EscapeRoom findByName() throws InputReadException {
        List<EscapeRoom> escapeRooms = escapeRoomService.getAllEscapeRooms();

        if (escapeRooms == null || escapeRooms.isEmpty()) {
            System.out.println("There are no Escape Rooms created yet. Please create one first.");
            return null;
        }

        System.out.println("\nAvailable Escape Rooms:");
        for (EscapeRoom er : escapeRooms) {
            System.out.println(" - " + er.getName());
        }

        EscapeRoom selectedEscapeRoom = null;

        while (selectedEscapeRoom == null) {
            System.out.print("Enter the name of the Escape Room: ");
            Name escapeRoomName = ConsoleInputReader.readName(scanner, "escape room name");

            for (EscapeRoom er : escapeRooms) {
                if (er.getName().toString().equalsIgnoreCase(escapeRoomName.toString())) {
                    selectedEscapeRoom = er;
                    break;
                }
            }

            if (selectedEscapeRoom == null) {
                System.out.println("No Escape Room found with name '" + escapeRoomName + "'. Please try again.\n");
            }
        }

        return selectedEscapeRoom;
    }
}
