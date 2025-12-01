package room.service;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;
import exceptions.NotFoundException;
import objectdecoration.dao.ObjectDecorationDaoImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import room.dao.RoomDaoImpl;
import room.model.DifficultyLevel;
import room.model.Room;


import java.math.BigDecimal;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceCountsTest {

    @Mock
    private RoomDaoImpl roomDao;

    @Mock
    private ObjectDecorationDaoImpl decorationDao;

    private RoomService roomService;

    private Room testRoom;
    private Id testRoomId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roomService = new RoomService(roomDao, decorationDao);
        
        testRoomId = new Id(1);
        testRoom = new Room(
                new Name("Mystery Room"),
                new Price(new BigDecimal("50.00")),
                new DifficultyLevel("MEDIUM")
        );
    }

    @Test
    @DisplayName("Should count clues in room successfully")
    void testCountCluesInRoom() {
      
        int expectedCount = 5;
        when(roomDao.countCluesByRoomId(testRoomId)).thenReturn(expectedCount);

        
        int actualCount = roomService.countCluesInRoom(testRoomId);

        
        assertEquals(expectedCount, actualCount);
        verify(roomDao, times(1)).countCluesByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should return zero when room has no clues")
    void testCountCluesInRoom_NoClues() {
      
        when(roomDao.countCluesByRoomId(testRoomId)).thenReturn(0);

        
        int actualCount = roomService.countCluesInRoom(testRoomId);

        
        assertEquals(0, actualCount);
        verify(roomDao, times(1)).countCluesByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should count decorations in room successfully")
    void testCountDecorationsInRoom() {
      
        int expectedCount = 3;
        when(roomDao.countDecorationsByRoomId(testRoomId)).thenReturn(expectedCount);

        
        int actualCount = roomService.countDecorationsInRoom(testRoomId);

        
        assertEquals(expectedCount, actualCount);
        verify(roomDao, times(1)).countDecorationsByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should return zero when room has no decorations")
    void testCountDecorationsInRoom_NoDecorations() {
      
        when(roomDao.countDecorationsByRoomId(testRoomId)).thenReturn(0);

        
        int actualCount = roomService.countDecorationsInRoom(testRoomId);

        
        assertEquals(0, actualCount);
        verify(roomDao, times(1)).countDecorationsByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should sum clues price successfully")
    void testSumCluesPriceInRoom() {
      
        Price expectedPrice = new Price(new BigDecimal("75.50"));
        when(roomDao.sumCluesPriceByRoomId(testRoomId)).thenReturn(expectedPrice);

        
        Price actualPrice = roomService.sumCluesPriceInRoom(testRoomId);

        
        assertEquals(expectedPrice.toBigDecimal(), actualPrice.toBigDecimal());
        verify(roomDao, times(1)).sumCluesPriceByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should return zero price when room has no clues")
    void testSumCluesPriceInRoom_NoClues() {
      
        Price zeroPrice = new Price(BigDecimal.ZERO);
        when(roomDao.sumCluesPriceByRoomId(testRoomId)).thenReturn(zeroPrice);

        
        Price actualPrice = roomService.sumCluesPriceInRoom(testRoomId);

        
        assertEquals(BigDecimal.ZERO, actualPrice.toBigDecimal());
        verify(roomDao, times(1)).sumCluesPriceByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should sum decorations price successfully")
    void testSumDecorationsPriceInRoom() {
      
        Price expectedPrice = new Price(new BigDecimal("120.00"));
        when(roomDao.sumDecorationsPriceByRoomId(testRoomId)).thenReturn(expectedPrice);

        
        Price actualPrice = roomService.sumDecorationsPriceInRoom(testRoomId);

        
        assertEquals(expectedPrice.toBigDecimal(), actualPrice.toBigDecimal());
        verify(roomDao, times(1)).sumDecorationsPriceByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should return zero price when room has no decorations")
    void testSumDecorationsPriceInRoom_NoDecorations() {
      
        Price zeroPrice = new Price(BigDecimal.ZERO);
        when(roomDao.sumDecorationsPriceByRoomId(testRoomId)).thenReturn(zeroPrice);

        
        Price actualPrice = roomService.sumDecorationsPriceInRoom(testRoomId);

        
        assertEquals(BigDecimal.ZERO, actualPrice.toBigDecimal());
        verify(roomDao, times(1)).sumDecorationsPriceByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should calculate total room price successfully")
    void testGetTotalRoomPrice() {
      
        Price roomPrice = new Price(new BigDecimal("50.00"));
        Price cluesPrice = new Price(new BigDecimal("75.50"));
        Price decorationsPrice = new Price(new BigDecimal("120.00"));
        BigDecimal expectedTotal = new BigDecimal("245.50");

        Room room = new Room(
                new Name("Mystery Room"),
                roomPrice,
                new DifficultyLevel("MEDIUM")
        );

        when(roomDao.findById(testRoomId)).thenReturn(Optional.of(room));
        when(roomDao.sumCluesPriceByRoomId(testRoomId)).thenReturn(cluesPrice);
        when(roomDao.sumDecorationsPriceByRoomId(testRoomId)).thenReturn(decorationsPrice);

        
        Price totalPrice = roomService.getTotalRoomPrice(testRoomId);

        
        assertEquals(expectedTotal, totalPrice.toBigDecimal());
        verify(roomDao, times(1)).findById(testRoomId);
        verify(roomDao, times(1)).sumCluesPriceByRoomId(testRoomId);
        verify(roomDao, times(1)).sumDecorationsPriceByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should throw NotFoundException when room does not exist for total price")
    void testGetTotalRoomPrice_RoomNotFound() {
      
        when(roomDao.findById(testRoomId)).thenReturn(Optional.empty());

        
        assertThrows(NotFoundException.class, () -> {
            roomService.getTotalRoomPrice(testRoomId);
        });

        verify(roomDao, times(1)).findById(testRoomId);
        verify(roomDao, never()).sumCluesPriceByRoomId(any());
        verify(roomDao, never()).sumDecorationsPriceByRoomId(any());
    }

    @Test
    @DisplayName("Should calculate total room price when no clues or decorations")
    void testGetTotalRoomPrice_OnlyRoomPrice() {
      
        Price roomPrice = new Price(new BigDecimal("50.00"));
        Price zeroPrice = new Price(BigDecimal.ZERO);

        Room room = new Room(
                new Name("Empty Room"),
                roomPrice,
                new DifficultyLevel("EASY")
        );

        when(roomDao.findById(testRoomId)).thenReturn(Optional.of(room));
        when(roomDao.sumCluesPriceByRoomId(testRoomId)).thenReturn(zeroPrice);
        when(roomDao.sumDecorationsPriceByRoomId(testRoomId)).thenReturn(zeroPrice);

        
        Price totalPrice = roomService.getTotalRoomPrice(testRoomId);

        
        assertEquals(new BigDecimal("50.00"), totalPrice.toBigDecimal());
    }

    @Test
    @DisplayName("Should get room statistics successfully")
    void testGetRoomStatistics() {
        // Given
        Price roomPrice = new Price(new BigDecimal("50.00"));
        Price cluesPrice = new Price(new BigDecimal("75.50"));
        Price decorationsPrice = new Price(new BigDecimal("120.00"));
        int clueCount = 5;
        int decorationCount = 3;

        Room room = new Room(
                new Name("Mystery Room"),
                roomPrice,
                new DifficultyLevel("MEDIUM")
        );

        when(roomDao.findById(testRoomId)).thenReturn(Optional.of(room));
        when(roomDao.countCluesByRoomId(testRoomId)).thenReturn(clueCount);
        when(roomDao.countDecorationsByRoomId(testRoomId)).thenReturn(decorationCount);
        when(roomDao.sumCluesPriceByRoomId(testRoomId)).thenReturn(cluesPrice);
        when(roomDao.sumDecorationsPriceByRoomId(testRoomId)).thenReturn(decorationsPrice);

        RoomStatistics statistics = roomService.getRoomStatistics(testRoomId);

        assertNotNull(statistics);
        assertEquals(room.getName(), statistics.getRoom().getName());
        assertEquals(clueCount, statistics.getClueCount());
        assertEquals(decorationCount, statistics.getDecorationCount());
        assertEquals(cluesPrice.toBigDecimal(), statistics.getCluesPrice().toBigDecimal());
        assertEquals(decorationsPrice.toBigDecimal(), statistics.getDecorationsPrice().toBigDecimal());
        assertEquals(new BigDecimal("245.50"), statistics.getTotalPrice().toBigDecimal());

        verify(roomDao, times(2)).findById(testRoomId);
        verify(roomDao, times(1)).countCluesByRoomId(testRoomId);
        verify(roomDao, times(1)).countDecorationsByRoomId(testRoomId);
        verify(roomDao, times(2)).sumCluesPriceByRoomId(testRoomId);
        verify(roomDao, times(2)).sumDecorationsPriceByRoomId(testRoomId);
    }

    @Test
    @DisplayName("Should throw NotFoundException when room does not exist for statistics")
    void testGetRoomStatistics_RoomNotFound() {
      
        when(roomDao.findById(testRoomId)).thenReturn(Optional.empty());

        
        assertThrows(NotFoundException.class, () -> {
            roomService.getRoomStatistics(testRoomId);
        });

        verify(roomDao, times(1)).findById(testRoomId);
        verify(roomDao, never()).countCluesByRoomId(any());
        verify(roomDao, never()).countDecorationsByRoomId(any());
    }

    @Test
    @DisplayName("Should get room statistics with zero counts and prices")
    void testGetRoomStatistics_EmptyRoom() {
      
        Price roomPrice = new Price(new BigDecimal("50.00"));
        Price zeroPrice = new Price(BigDecimal.ZERO);

        Room room = new Room(
                new Name("Empty Room"),
                roomPrice,
                new DifficultyLevel("EASY")
        );

        when(roomDao.findById(testRoomId)).thenReturn(Optional.of(room));
        when(roomDao.countCluesByRoomId(testRoomId)).thenReturn(0);
        when(roomDao.countDecorationsByRoomId(testRoomId)).thenReturn(0);
        when(roomDao.sumCluesPriceByRoomId(testRoomId)).thenReturn(zeroPrice);
        when(roomDao.sumDecorationsPriceByRoomId(testRoomId)).thenReturn(zeroPrice);

        
        RoomStatistics statistics = roomService.getRoomStatistics(testRoomId);

        
        assertNotNull(statistics);
        assertEquals(0, statistics.getClueCount());
        assertEquals(0, statistics.getDecorationCount());
        assertEquals(BigDecimal.ZERO, statistics.getCluesPrice().toBigDecimal());
        assertEquals(BigDecimal.ZERO, statistics.getDecorationsPrice().toBigDecimal());
        assertEquals(new BigDecimal("50.00"), statistics.getTotalPrice().toBigDecimal());
    }

    @Test
    @DisplayName("Should handle multiple rooms with different statistics")
    void testGetRoomStatistics_MultipleRooms() {
      
        Id room1Id = new Id(1);
        Id room2Id = new Id(2);

        Room room1 = new Room(
                new Name("Easy Room"),
                new Price(new BigDecimal("30.00")),
                new DifficultyLevel("EASY")
        );

        Room room2 = new Room(
                new Name("Hard Room"),
                new Price(new BigDecimal("100.00")),
                new DifficultyLevel("HARD")
        );
        
        when(roomDao.findById(room1Id)).thenReturn(Optional.of(room1));
        when(roomDao.countCluesByRoomId(room1Id)).thenReturn(2);
        when(roomDao.countDecorationsByRoomId(room1Id)).thenReturn(1);
        when(roomDao.sumCluesPriceByRoomId(room1Id)).thenReturn(new Price(new BigDecimal("20.00")));
        when(roomDao.sumDecorationsPriceByRoomId(room1Id)).thenReturn(new Price(new BigDecimal("10.00")));
        
        when(roomDao.findById(room2Id)).thenReturn(Optional.of(room2));
        when(roomDao.countCluesByRoomId(room2Id)).thenReturn(10);
        when(roomDao.countDecorationsByRoomId(room2Id)).thenReturn(8);
        when(roomDao.sumCluesPriceByRoomId(room2Id)).thenReturn(new Price(new BigDecimal("150.00")));
        when(roomDao.sumDecorationsPriceByRoomId(room2Id)).thenReturn(new Price(new BigDecimal("200.00")));

        
        RoomStatistics stats1 = roomService.getRoomStatistics(room1Id);
        RoomStatistics stats2 = roomService.getRoomStatistics(room2Id);

        
        assertEquals(2, stats1.getClueCount());
        assertEquals(new BigDecimal("60.00"), stats1.getTotalPrice().toBigDecimal());

        assertEquals(10, stats2.getClueCount());
        assertEquals(new BigDecimal("450.00"), stats2.getTotalPrice().toBigDecimal());
    }

    @Test
    @DisplayName("Should verify correct method calls order")
    void testGetRoomStatistics_MethodCallOrder() {
      
        Room room = new Room(
                new Name("Test Room"),
                new Price(new BigDecimal("50.00")),
                new DifficultyLevel("MEDIUM")
        );

        when(roomDao.findById(testRoomId)).thenReturn(Optional.of(room));
        when(roomDao.countCluesByRoomId(testRoomId)).thenReturn(5);
        when(roomDao.countDecorationsByRoomId(testRoomId)).thenReturn(3);
        when(roomDao.sumCluesPriceByRoomId(testRoomId)).thenReturn(new Price(new BigDecimal("50.00")));
        when(roomDao.sumDecorationsPriceByRoomId(testRoomId)).thenReturn(new Price(new BigDecimal("50.00")));

        
        roomService.getRoomStatistics(testRoomId);
        
        var inOrder = inOrder(roomDao);
        inOrder.verify(roomDao).findById(testRoomId);
        inOrder.verify(roomDao).countCluesByRoomId(testRoomId);
        inOrder.verify(roomDao).countDecorationsByRoomId(testRoomId);
        inOrder.verify(roomDao).sumCluesPriceByRoomId(testRoomId);
        inOrder.verify(roomDao).sumDecorationsPriceByRoomId(testRoomId);
    }
}