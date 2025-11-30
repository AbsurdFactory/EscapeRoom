package ticket.model;

import commonValueObjects.Id;
import commonValueObjects.Name;
import commonValueObjects.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private Id id;
    private Id roomId;
    private Id playerId;
    private Price price;
    private LocalDateTime localDateTime;
    private Name playerName;
    private Name roomName;

    public Ticket() {
    }

    public Ticket(Id id, Id roomId, Id playerId, LocalDateTime localDateTime, Price price) {

        this.id = id;
        this.roomId = roomId;
        this.playerId = playerId;
        this.price = price;
        this.localDateTime = Objects.requireNonNull(localDateTime, "Time cannot be null.");
    }

    public Ticket(int id, int roomId, int playerId, LocalDateTime localDateTime, BigDecimal price) {
        if (id <= 0) {
            throw new IllegalArgumentException("Ticket id must be greater than 0.");
        }
        if (roomId <= 0) {
            throw new IllegalArgumentException("Room id must be greater than 0.");
        }
        if (playerId <= 0) {
            throw new IllegalArgumentException("Player id must be greater than 0.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be null and must be greater than 0.");
        }

        this.id = new Id<>(id);
        this.roomId = new Id<>(roomId);
        this.playerId = new Id<>(playerId);
        this.price = new Price(price);
        this.localDateTime = Objects.requireNonNull(localDateTime, "Time cannot be null.");
    }

    public Ticket(Name playerName, Name roomName, LocalDateTime localDateTime, BigDecimal price) {
        this.playerName = playerName;
        this.roomName = roomName;
        this.price = new Price(price);
        this.localDateTime = Objects.requireNonNull(localDateTime, "Time cannot be null.");
    }

    public Ticket(Name playerName, Name roomName, BigDecimal price) {
        this.playerName = playerName;
        this.roomName = roomName;
        this.price = new Price(price);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Name getPlayerName() {
        return playerName;
    }

    public Name getRoomName() {
        return roomName;
    }

    public Id getId() {
        return id;
    }

    public Id getRoomId() {
        return roomId;
    }

    public Id getPlayerId() {
        return playerId;
    }

    public LocalDateTime getDateTime() {
        return localDateTime;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket" + id +
                "\nroom =" + roomName +
                "\nplayer " + playerName +
                "\nDateTime " + localDateTime +
                "\nprice " + price;
    }
}
