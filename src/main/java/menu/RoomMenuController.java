package menu;

import commonValueObjects.Name;
import commonValueObjects.Price;
import room.dao.RoomDao;
import room.dao.RoomDaoImpl;
import room.model.DifficultyLevel;
import room.model.Room;
import room.model.RoomBuilder;
import room.service.RoomService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class RoomMenuController extends BaseMenuController {
    private final RoomService roomService;
    private final RoomDaoImpl roomDao = new RoomDaoImpl();

    public RoomMenuController(Scanner scanner) {
        super(scanner);
        this.roomService = new RoomService(roomDao);
    }

       public void showRoomMenu() {
        int option;

        do {
            MenuPrinter.displayRoomMenu();
            option = askOption("Choose an option (1-3): ", 1, 3);

            switch (option) {
                case 1:
                    handleCreateRoom();
                    break;
                case 2:
                    handleListRooms();
                    break;
                case 3:
                    System.out.println("\nReturning to previous menu...");
                    break;
            }

        } while (option != 3);
    }


    private void handleCreateRoom() {
        System.out.println("\n--- Create new Room ---");

        try {
            System.out.print("Please enter the Room name: ");
            Name name = ConsoleInputReader.readName(scanner, "Room name");

            System.out.println("Please enter the difficulty level (\"Easy\", \"Medium\", \"Hard\")");
            DifficultyLevel difficulty = ConsoleInputReader.readDifficultyLevel(scanner, "difficulty level");

            System.out.println("Please enter the base price of the Room.");
            Price basePrice = ConsoleInputReader.readPrice(scanner, "base price of the Room");

            Room room = new RoomBuilder()
                   .withName(name)
                   .withDifficultyLevel(difficulty)
                   .withPrice(basePrice)
                   .build();
            roomService.createRoom(room);

        } catch (InputReadException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected error while creating the Room.");
        }
    }


    private void handleListRooms() {
        System.out.println("\n--- List of Rooms ---");

        try {
            List<Room> rooms = roomService.getAllRooms();

            if (rooms == null || rooms.isEmpty()) {
                System.out.println("There are no rooms registered yet.");
                return;
            }

            for (Room room : rooms) {

                System.out.println("------------------------------");
                System.out.println("ID: " + room.getId());
                System.out.println("Name: " + room.getName());

            }
            System.out.println("------------------------------");

        } catch (Exception e) {
            System.out.println("\nERROR retrieving Rooms list.");
        }
    }

    @Override
    public void showMenu() throws InputReadException {
        showRoomMenu();
    }
}
