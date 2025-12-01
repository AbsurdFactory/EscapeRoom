package menu;

import clue.dao.ClueDaoImplementation;
import clue.model.Clue;
import clue.model.ClueText;
import clue.model.ClueTheme;
import clue.service.ClueService;
import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.ObjectDecoration;
import objectdecoration.service.ObjectDecorationService;
import org.junit.jupiter.api.*;
import room.dao.RoomDaoImpl;
import room.model.DifficultyLevel;
import room.model.Room;
import room.model.RoomBuilder;
import room.service.RoomService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoomMenuControllerTest {

    private RoomMenuController controller;

    private RoomDaoImpl roomDao;
    private RoomService roomService;

    private ClueDaoImplementation clueDao;
    private ClueService clueService;

    private ObjectDecorationDaoImpl decorationDao;
    private ObjectDecorationService decorationService;

    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        roomDao = new RoomDaoImpl();
        roomService = new RoomService(roomDao);

        clueDao = new ClueDaoImplementation();
        clueService = new ClueService(clueDao);

        decorationDao = new ObjectDecorationDaoImpl();
        decorationService = new ObjectDecorationService(decorationDao);

        cleanTestData();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        cleanTestData();
    }

    private void cleanTestData() {
        try {
            roomDao.deleteByName(new Name("Menu Test Room"));
            roomDao.deleteByName(new Name("Room With Clue"));
            roomDao.deleteByName(new Name("Room With Deco"));
            roomDao.deleteByName(new Name("Details Room"));

            clueDao.deleteByName(new Name("Test Menu Clue"));
            decorationDao.deleteByName(new Name("Test Menu Deco"));
        } catch (Exception ignored) {
        }
    }

    @Test
    @Order(1)
    @DisplayName("Should create RoomMenuController with a Scanner")
    void testConstructor() {
        Scanner scanner = new Scanner(System.in);
        controller = new RoomMenuController(scanner);

        assertNotNull(controller);
    }

    @Test
    @Order(2)
    @DisplayName("Should create a room from the menu and persist it")
    void testCreateRoomFromMenu_persistsRoom() {
        String input = String.join(System.lineSeparator(),
                "1",
                "Menu Test Room",
                "Easy",
                "10,50",
                "8"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        controller = new RoomMenuController(scanner);

        controller.showMenu();

        List<Room> rooms = roomService.getAllRooms();
        assertTrue(
                rooms.stream().anyMatch(r -> r.getName().toString().equals("Menu Test Room")),
                "Room 'Menu Test Room' should have been created in the database."
        );

        String console = outContent.toString();
        assertTrue(console.contains("Room created successfully"),
                "Console output should confirm room creation.");
    }

    @Test
    @Order(3)
    @DisplayName("Should show room details from menu using existing room")
    void testShowRoomDetailsFromMenu_printsDetails() {
        Room detailsRoom = new RoomBuilder()
                .withName(new Name("Details Room"))
                .withDifficultyLevel(new DifficultyLevel("Medium"))
                .withPrice(new Price(new BigDecimal("25.00")))
                .build();
        roomService.createRoom(detailsRoom);

        String input = String.join(System.lineSeparator(),
                "2",
                "Details Room",
                "8"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        controller = new RoomMenuController(scanner);

        controller.showMenu();

        String console = outContent.toString();
        assertTrue(console.contains("=== ROOM DETAILS ==="),
                "Console should print room details header.");
        assertTrue(console.contains("Details Room"),
                "Console should contain the room name.");
        assertTrue(console.contains("Medium"),
                "Console should contain the difficulty.");
    }

    @Test
    @Order(4)
    @DisplayName("Should list all rooms from menu")
    void testListAllRoomsFromMenu_printsRooms() {
        Room room = new RoomBuilder()
                .withName(new Name("Menu Test Room"))
                .withDifficultyLevel(new DifficultyLevel("Easy"))
                .withPrice(new Price(new BigDecimal("15.00")))
                .build();
        roomService.createRoom(room);

        String input = String.join(System.lineSeparator(),
                "3",
                "8"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        controller = new RoomMenuController(scanner);

        controller.showMenu();

        String console = outContent.toString();
        assertTrue(console.contains("Rooms Available"),
                "Console should print rooms list header.");
        assertTrue(console.contains("Menu Test Room"),
                "Console should show the room name in the list.");
    }

    @Test
    @Order(5)
    @DisplayName("Should add a clue to room from menu and delegate to RoomService")
    void testAddClueToRoomFromMenu() {
        Room room = new RoomBuilder()
                .withName(new Name("Room With Clue"))
                .withDifficultyLevel(new DifficultyLevel("Easy"))
                .withPrice(new Price(new BigDecimal("20.00")))
                .build();
        roomService.createRoom(room);

        Clue clue = new Clue(
                new Name("Test Menu Clue"),
                new ClueText("Some clue text"),
                new ClueTheme("Generic"),
                new Price(new BigDecimal("5.00"))
        );
        clueService.createClue(clue);

        String input = String.join(System.lineSeparator(),
                "4",
                "Room With Clue",
                "Test Menu Clue",
                "8"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        controller = new RoomMenuController(scanner);

        controller.showMenu();

        String console = outContent.toString();
        assertTrue(console.contains("Clue 'Test Menu Clue' added to Room 'Room With Clue'"),
                "Console should confirm clue added to room.");
    }

    @Test
    @Order(6)
    @DisplayName("Should add a decoration to room from menu and delegate to RoomService")
    void testAddDecorationToRoomFromMenu() {

        Room room = new RoomBuilder()
                .withName(new Name("Room With Deco"))
                .withDifficultyLevel(new DifficultyLevel("Hard"))
                .withPrice(new Price(new BigDecimal("35.00")))
                .build();
        roomService.createRoom(room);


        ObjectDecoration deco = ObjectDecoration.rehydrate(
                0,
                "Test Menu Deco",
                "Wood",
                12.50
        );
        decorationService.addObjectDecoration(deco);

        String input = String.join(System.lineSeparator(),
                "6",
                "Room With Deco",
                "Test Menu Deco",
                "8"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        controller = new RoomMenuController(scanner);

        controller.showMenu();

        String console = outContent.toString();
        assertTrue(console.contains("Decoration 'Test Menu Deco' added to Room 'Room With Deco'"),
                "Console should confirm decoration added to room.");
    }

    @Test
    @Order(7)
    @DisplayName("Should navigate menu and exit without errors")
    void testMenuNavigationExit() {
        String input = String.join(System.lineSeparator(),
                "8"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        controller = new RoomMenuController(scanner);

        assertDoesNotThrow(() -> controller.showMenu());
    }
}
