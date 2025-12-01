package room.service;

import commonValueObjects.Price;
import room.model.Room;

public class RoomStatistics {
    private final Room room;
    private final int clueCount;
    private final int decorationCount;
    private final Price cluesPrice;
    private final Price decorationsPrice;
    private final Price totalPrice;

    public RoomStatistics(Room room, int clueCount, int decorationCount,
                          Price cluesPrice, Price decorationsPrice, Price totalPrice) {
        this.room = room;
        this.clueCount = clueCount;
        this.decorationCount = decorationCount;
        this.cluesPrice = cluesPrice;
        this.decorationsPrice = decorationsPrice;
        this.totalPrice = totalPrice;
    }

    public Room getRoom() {
        return room;
    }

    public int getClueCount() {
        return clueCount;
    }

    public int getDecorationCount() {
        return decorationCount;
    }

    public Price getCluesPrice() {
        return cluesPrice;
    }

    public Price getDecorationsPrice() {
        return decorationsPrice;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "RoomStatistics{" +
                "room=" + room.getName() +
                ", clueCount=" + clueCount +
                ", decorationCount=" + decorationCount +
                ", cluesPrice=" + cluesPrice.toBigDecimal() +
                ", decorationsPrice=" + decorationsPrice.toBigDecimal() +
                ", totalPrice=" + totalPrice.toBigDecimal() +
                '}';
    }
}
