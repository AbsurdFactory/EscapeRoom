package inventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    InventoryService inventoryService = new InventoryService();
    @Test
    @DisplayName("Return the price for all the clues in the database")
    void getCluesTotalPrice() {
        assertTrue(inventoryService.getCluesTotalPrice().compareTo(BigDecimal.ZERO)>= 0);
    }

    @Test
    @DisplayName("Return the price for all the object decorations in the database")
    void testGetObjectDecorationsTotalPrice() {
        assertTrue(inventoryService.getObjectDecorationsTotalPrice().compareTo(BigDecimal.ZERO)>= 0);

    }

    @Test
    @DisplayName("Return the price for all the clues and object decorations in the database")
    void testGetTotalValueOfInventory() {
        assertTrue(inventoryService.getTotalValueOfInventory().compareTo(BigDecimal.ZERO)>= 0 );

    }

    @Test
    @DisplayName("Return the total quantity of items in the database")
    void testGetAllItemsQuantitiesFromInventory() {
        assertTrue(inventoryService.getAllItemsQuantitiesFromInventory()>=0);
    }

    @Test
    @DisplayName("Return the total quantity of rooms in the database")
    void testGetAllRoomsFromInventory() {
        assertTrue(inventoryService.getAllRoomsFromInventory()>=0);

    }

    @Test
    @DisplayName("Return the total quantity of the clues in the database")
    void testGetAllCluesFromInventory() {
        assertTrue(inventoryService.getAllCluesFromInventory()>=0);

    }

    @Test
    @DisplayName("Return the total quantity of object decorations in the database")
    void testGetAllObjectDecorationsFromInventory() {
        assertTrue(inventoryService.getAllObjectDecorationsFromInventory()>=0);
    }


}