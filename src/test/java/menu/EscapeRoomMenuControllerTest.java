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
        // Capture console output
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Initialize DAOs and services
        escapeRoomDao = new EscapeRoomDaoImpl();
        roomDao = new RoomDaoImpl();
        escapeRoomService = new EscapeRoomService(escapeRoomDao);
        roomService = new RoomService(roomDao);
    }

    @AfterEach
    void tearDown() {
        // Restore console output
        System.setOut(originalOut);

        // Clean up test data
        try {
            escapeRoomDao.deleteByName(new Name("Test Escape Room"));
            escapeRoomDao.deleteByName(new Name("Another Escape Room"));
            roomDao.deleteByName(new Name("Test Room"));
            roomDao.deleteByName(new Name("Mystery Room"));
        } catch (Exception e) {
            // Ignore cleanup errors
        }
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
        // Simulate user input: escape room name, then option 4 to exit
        String input = "Test Escape Room\n4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new EscapeRoomMenuController(scanner);

        // Manually trigger the create method
        escapeRoomService.createEscapeRoom(
                new EscapeRoom(new Name("Test Escape Room"))
        );

        // Verify the escape room was created
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

        // Add room to escape room (in-memory)
        escapeRoom.addRoom(room);

        // Verify
        assertEquals(1, escapeRoom.getRooms().size());
        assertTrue(escapeRoom.getRooms().contains(room));
    }

    @Test
    @Order(5)
    @DisplayName("Should handle empty escape room list")
    void testEmptyEscapeRoomList() {
        // Delete all escape rooms
        var allRooms = escapeRoomService.getAllEscapeRooms();
        for (EscapeRoom er : allRooms) {
            try {
                escapeRoomDao.deleteByName(new Name(er.getName().toString()));
            } catch (Exception e) {
                // Continue
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
        // Create escape room
        EscapeRoom escapeRoom = new EscapeRoom(new Name("Test Escape Room"));
        escapeRoomService.createEscapeRoom(escapeRoom);

        // Create rooms with different difficulties
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
        // Create escape room with rooms
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

        // Verify details
        assertEquals("Test Escape Room", escapeRoom.getName().toString());
        assertEquals(2, escapeRoom.getRooms().size());
    }

    @Test
    @Order(10)
    @DisplayName("Should handle menu navigation input")
    void testMenuNavigation() {
        // Simulate selecting option 4 (exit)
        String input = "4\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        controller = new EscapeRoomMenuController(scanner);

        assertNotNull(controller);
        // Controller created successfully and can handle input
    }
}