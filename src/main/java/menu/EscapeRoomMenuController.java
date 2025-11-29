package menu;

import escaperoom.dao.EscapeRoomDao;
import escaperoom.dao.EscapeRoomDaoImpl;
import escaperoom.model.EscapeRoom;
import escaperoom.model.EscapeRoomBuilder;
import escaperoom.service.EscapeRoomService;
import room.dao.RoomDao;
import room.dao.RoomDaoImpl;
import room.model.Room;
import room.model.RoomBuilder;
import room.service.RoomService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class EscapeRoomMenuController extends BaseMenuController {

    private final EscapeRoomService escapeRoomService;
    private final RoomService roomService;
    private final EscapeRoomDao escapeRoomDao = new EscapeRoomDaoImpl();
    private final RoomDao roomDao = new RoomDaoImpl();

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
            String name = ConsoleInputReader.readNonEmptyString(scanner, "Escape Room name");

            EscapeRoomBuilder builder = new EscapeRoomBuilder()
                    .withName(name);

            EscapeRoom escapeRoom = builder.build();
            escapeRoomService.createEscapeRoom(escapeRoom);

            System.out.println("\nEscape Room created successfully!");

        } catch (InputReadException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected error while creating the Escape Room.");
        }
    }

    //TODO: quitar validaciones
    private void addRoomToEscapeRoom() throws InputReadException {
        System.out.println("\n--- Add Room to Escape Room ---");
        List<EscapeRoom> escapeRooms = escapeRoomService.getAllEscapeRooms();

        if (escapeRooms == null || escapeRooms.isEmpty()) {
            System.out.println("There are no Escape Rooms created yet. Please create one first.");
        }

        System.out.println("\nAvailable Escape Rooms:");
        for (EscapeRoom er : escapeRooms) {
            System.out.println(" - " + er.getName());
        }

        EscapeRoom selectedEscapeRoom = null;

        while (selectedEscapeRoom == null) {
            System.out.print("Enter the name of the Escape Room you want to add the Room to: ");
            String escapeRoomName = ConsoleInputReader.readNonEmptyString(scanner, "escape room name");

            for (EscapeRoom er : escapeRooms) {
                if (er.getName().equalsIgnoreCase(escapeRoomName)) {
                    selectedEscapeRoom = er;
                    break;
                }
            }

            if (selectedEscapeRoom == null) {
                System.out.println("No Escape Room found with name '" + escapeRoomName + "'. Please try again.\n");
            }
        }

        RoomBuilder roomBuilder = new RoomBuilder();

        System.out.println("Please enter the name of the Room.");
        String roomName = ConsoleInputReader.readNonEmptyString(scanner, "room name");

        String difficultyLevel;
        while (true) {
            System.out.println("Please enter the difficulty level (\"Easy\", \"Medium\", \"Hard\")");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Easy") ||
                    input.equalsIgnoreCase("Medium") ||
                    input.equalsIgnoreCase("Hard")) {

                difficultyLevel = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
                break;
            } else {
                System.out.println("Invalid difficulty. Please enter Easy, Medium, or Hard.");
            }
        }

        System.out.println("Please enter the base price of the Room.");
        BigDecimal price = ConsoleInputReader.readBigDecimal(scanner, "room price");

        Room room = roomBuilder
                .withName(roomName)
                .withDifficultyLevel(difficultyLevel)
                .withPrice(price)
                .build();

        roomService.createRoom(room);

        selectedEscapeRoom.addRoom(room);

        System.out.println("\nThe room was successfully created and added to Escape Room '"
                + selectedEscapeRoom.getName() + "'.");

    }


    private void showEscapeRoomDetails() {
        System.out.println("\n--- Show Escape Room Details ---");

        try {
            System.out.print("Enter the Escape Room ID: ");
            int id = ConsoleInputReader.readInt(scanner);

            escapeRoomService.getEscapeRoomById(id).ifPresentOrElse(
                    escapeRoom -> {
                        System.out.println("\n=== ESCAPE ROOM DETAILS ===");
                        System.out.println("Name: " + escapeRoom.getName());
                        System.out.println("Rooms: " + escapeRoom.getRooms().size());
                        System.out.println("=============================");
                    },
                    () -> System.out.println("No Escape Room found with ID: " + id)
            );

        } catch (InputReadException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }


    @Override
    public void showMenu() throws InputReadException {
        showEscapeRoomMenu();
    }
}
