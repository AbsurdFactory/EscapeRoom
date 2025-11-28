package inventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    InventoryService inventoryService = new InventoryService();
    @Test
    @DisplayName("Return the price for all the clues in the database")
    void getCluePrice() {
        assertTrue(inventoryService.getCluesTotalPrice()>0);
    }

    @Test
    @DisplayName("Return the price for all the object decorations in the database")
    void getObjectDecorationsTotalPrice() {
    }

    @Test
    @DisplayName("Return the price for all the clues and object decorations in the database")
    void getTotalValueOfInventory() {
    }

    @Test
    @DisplayName("Return the total quantity of items in the database")
    void getAllItemsQuantitiesFromInventory() {
    }

    @Test
    @DisplayName("Return the total quantity of rooms in the database")
    void getAllRoomsFromInventory() {
    }

    @Test
    @DisplayName("Return the total quantity of the clues in the database")
    void getAllCluesFromInventory() {
    }

    @Test
    @DisplayName("Return the total quantity of object decorations in the database")
    void getAllObjectDecorationsFromInventory() {
    }
}