package menu;

import commonValueObjects.Name;
import commonValueObjects.Price;
import escaperoom.dao.EscapeRoomDaoImpl;
import escaperoom.model.EscapeRoom;
import escaperoom.service.EscapeRoomService;
import org.junit.jupiter.api.*;
import room.dao.RoomDaoImpl;
import room.model.DifficultyLevel;
import room.model.Room;
import room.service.RoomService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EscapeRoomMenuControllerTest {

    private EscapeRoomMenuController controller;
    private EscapeRoomDaoImpl escapeRoomDao;
    private RoomDaoImpl roomDao;
    private EscapeRoomService escapeRoomService;
    private RoomService roomService;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        escapeRoomDao = new EscapeRoomDaoImpl();
        roomDao = new RoomDaoImpl();
        escapeRoomService = new EscapeRoomService(escapeRoomDao);
        roomService = new RoomService(roomDao);

        cleanTestData();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);

        cleanTestData();
    }

    @Test
    @Order(1)
    @DisplayName("Should create escape room menu controller with scanner")
    void testConstructor() {
        Scanner scanner = new Scanner(System.in);
        controller = new EscapeRoomMenuController(scanner);

        assertNotNull(controller);
    }

    @Test
    @Order(2)
    @DisplayName("Should create a new escape room successfully")
    void testCreateEscapeRoom() throws InputReadException {
        String input = "Test Escape Room\n4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new EscapeRoomMenuController(scanner);

        escapeRoomService.createEscapeRoom(
                new EscapeRoom(new Name("Test Escape Room"))
        );

        var escapeRooms = escapeRoomService.getAllEscapeRooms();
        assertTrue(escapeRooms.stream()
                .anyMatch(er -> er.getName().toString().equals("Test Escape Room")));
    }

    @Test
    @Order(3)
    @DisplayName("Should retrieve all escape rooms")
    void testGetAllEscapeRooms() {
        escapeRoomService.createEscapeRoom(new EscapeRoom(new Name("Test Escape Room")));
        escapeRoomService.createEscapeRoom(new EscapeRoom(new Name("Another Escape Room")));

        var escapeRooms = escapeRoomService.getAllEscapeRooms();

        assertNotNull(escapeRooms);
        assertTrue(escapeRooms.size() >= 2);
        assertTrue(escapeRooms.stream()
                .anyMatch(er -> er.getName().toString().equals("Test Escape Room")));
        assertTrue(escapeRooms.stream()
                .anyMatch(er -> er.getName().toString().equals("Another Escape Room")));
    }

    @Test
    @Order(4)
    @DisplayName("Should add room to escape room successfully")
    void testAddRoomToEscapeRoom() throws InputReadException {
        EscapeRoom escapeRoom = new EscapeRoom(new Name("Test Escape Room"));
        escapeRoomService.createEscapeRoom(escapeRoom);

        Room room = new Room(
                new Name("Test Room"),
                new Price(new BigDecimal("50.00")),
                new DifficultyLevel("Medium")
        );
        roomService.createRoom(room);

        escapeRoom.addRoom(room);

        assertEquals(1, escapeRoom.getRooms().size());
        assertTrue(escapeRoom.getRooms().contains(room));
    }

    @Test
    @Order(5)
    @DisplayName("Should handle empty escape room list")
    void testEmptyEscapeRoomList() {
        var allRooms = escapeRoomService.getAllEscapeRooms();
        for (EscapeRoom er : allRooms) {
            try {
                escapeRoomDao.deleteByName(new Name(er.getName().toString()));
            } catch (Exception e) {
            }
        }

        var escapeRooms = escapeRoomService.getAllEscapeRooms();

        assertNotNull(escapeRooms);
        assertTrue(escapeRooms.isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Should create multiple rooms with different difficulty levels")
    void testCreateMultipleRoomsWithDifferentDifficulties() {
        EscapeRoom escapeRoom = new EscapeRoom(new Name("Test Escape Room"));
        escapeRoomService.createEscapeRoom(escapeRoom);

        Room easyRoom = new Room(
                new Name("Easy Room"),
                new Price(new BigDecimal("30.00")),
                new DifficultyLevel("Easy")
        );
        Room hardRoom = new Room(
                new Name("Hard Room"),
                new Price(new BigDecimal("80.00")),
                new DifficultyLevel("Hard")
        );

        roomService.createRoom(easyRoom);
        roomService.createRoom(hardRoom);

        escapeRoom.addRoom(easyRoom);
        escapeRoom.addRoom(hardRoom);

        assertEquals(2, escapeRoom.getRooms().size());
        assertTrue(escapeRoom.getRooms().stream()
                .anyMatch(r -> r.getDifficultyLevel().isEasy()));
        assertTrue(escapeRoom.getRooms().stream()
                .anyMatch(r -> r.getDifficultyLevel().isHard()));
    }

    @Test
    @Order(7)
    @DisplayName("Should find escape room by name")
    void testFindEscapeRoomByName() {
        escapeRoomService.createEscapeRoom(new EscapeRoom(new Name("Mystery Room Escape")));

        var escapeRooms = escapeRoomService.getEscapeRoomsByName("Mystery Room Escape");

        assertNotNull(escapeRooms);
        assertFalse(escapeRooms.isEmpty());
        assertEquals("Mystery Room Escape", escapeRooms.get(0).getName().toString());
    }

    @Test
    @Order(8)
    @DisplayName("Should handle invalid escape room name gracefully")
    void testInvalidEscapeRoomName() {
        var escapeRooms = escapeRoomService.getEscapeRoomsByName("Nonexistent Room");

        assertNotNull(escapeRooms);
        assertTrue(escapeRooms.isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName("Should verify escape room details display")
    void testEscapeRoomDetails() {
        EscapeRoom escapeRoom = new EscapeRoom(new Name("Test Escape Room"));
        escapeRoomService.createEscapeRoom(escapeRoom);

        Room room1 = new Room(
                new Name("Room 1"),
                new Price(new BigDecimal("40.00")),
                new DifficultyLevel("Easy")
        );
        Room room2 = new Room(
                new Name("Room 2"),
                new Price(new BigDecimal("60.00")),
                new DifficultyLevel("Hard")
        );

        roomService.createRoom(room1);
        roomService.createRoom(room2);

        escapeRoom.addRoom(room1);
        escapeRoom.addRoom(room2);


        assertEquals("Test Escape Room", escapeRoom.getName().toString());
        assertEquals(2, escapeRoom.getRooms().size());
    }

    @Test
    @Order(10)
    @DisplayName("Should handle menu navigation input")
    void testMenuNavigation() {
        String input = "4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new EscapeRoomMenuController(scanner);

        assertNotNull(controller);
    }

    @Test
    @Order(11)
    @DisplayName("Should add room to escape room from menu and persist relation")
    void testAddRoomToEscapeRoomFromMenu_persistsRelation() throws InputReadException {
        escapeRoomService.createEscapeRoom(new EscapeRoom(new Name("Menu Base Escape")));

        var escapeRooms = escapeRoomService.getEscapeRoomsByName("Menu Base Escape");
        assertFalse(escapeRooms.isEmpty(), "Base escape room should exist in DB");
        EscapeRoom persistedEscapeRoom = escapeRooms.get(0);


        String input = String.join(System.lineSeparator(),
                "2",
                "Menu Base Escape",
                "Menu Room 1",
                "Easy",
                "10,50",
                "4"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new EscapeRoomMenuController(scanner);

        controller.showEscapeRoomMenu();


        var roomIdOpt = roomDao.getIdByName("Menu Room 1");
        assertTrue(roomIdOpt.isPresent(), "Room 'Menu Room 1' should exist in DB");
        int roomId = roomIdOpt.get();


        var roomIdsForEscape = escapeRoomService.getRoomIdsForEscapeRoom(persistedEscapeRoom);
        assertTrue(roomIdsForEscape.contains(roomId),
                "escape_has_room should contain relation between 'Menu Base Escape' and 'Menu Room 1'");


        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("was successfully created and linked to Escape Room"),
                "Console output should confirm that the Room was created and linked.");
    }

    @Test
    @Order(12)
    @DisplayName("Should show escape room details using persisted rooms count")
    void testShowEscapeRoomDetails_usesPersistedRelation() throws InputReadException {

        escapeRoomService.createEscapeRoom(new EscapeRoom(new Name("Details Escape Menu")));
        var escapeRooms = escapeRoomService.getEscapeRoomsByName("Details Escape Menu");
        assertFalse(escapeRooms.isEmpty(), "Details escape room should exist in DB");
        EscapeRoom persistedEscapeRoom = escapeRooms.get(0);

        Room room = new Room(
                new Name("Details Menu Room"),
                new Price(new BigDecimal("15.00")),
                new DifficultyLevel("Easy")
        );
        roomService.createRoom(room);

        var roomIdOpt = roomDao.getIdByName("Details Menu Room");
        assertTrue(roomIdOpt.isPresent(), "Room 'Details Menu Room' should exist in DB");
        int roomId = roomIdOpt.get();


        escapeRoomService.addRoomToEscapeRoom(persistedEscapeRoom, roomId);


        String input = String.join(System.lineSeparator(),
                "3",
                "Details Escape Menu",
                "4"
        ) + System.lineSeparator();

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new EscapeRoomMenuController(scanner);

        controller.showEscapeRoomMenu();


        String consoleOutput = outputStream.toString();
        assertTrue(consoleOutput.contains("Details Escape Menu"),
                "Console output should contain the Escape Room name.");
        assertTrue(consoleOutput.contains("Rooms: 1"),
                "Console output should show 'Rooms: 1' in the details view.");
    }

    private void cleanTestData() {
        try {
            escapeRoomDao.deleteByName(new Name("Test Escape Room"));
            escapeRoomDao.deleteByName(new Name("Another Escape Room"));
            escapeRoomDao.deleteByName(new Name("Menu Base Escape"));
            escapeRoomDao.deleteByName(new Name("Details Escape Menu"));
            escapeRoomDao.deleteByName(new Name("Mystery Room Escape"));

            roomDao.deleteByName(new Name("Test Room"));
            roomDao.deleteByName(new Name("Mystery Room"));
            roomDao.deleteByName(new Name("Easy Room"));
            roomDao.deleteByName(new Name("Hard Room"));
            roomDao.deleteByName(new Name("Room 1"));
            roomDao.deleteByName(new Name("Room 2"));
            roomDao.deleteByName(new Name("Menu Room 1"));
            roomDao.deleteByName(new Name("Details Menu Room"));
        } catch (Exception ignored) {

        }
    }
}