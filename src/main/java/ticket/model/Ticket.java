package ticket.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Ticket {
    private int id;
    private int roomId;
    private int playerId;
    private LocalDate date;
    private LocalTime time;
    private BigDecimal price;

    public Ticket() {
    }

    public Ticket(int id, int roomId, int playerId, LocalDate date, LocalTime time, BigDecimal price) {
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
        this.id = id;
        this.roomId = roomId;
        this.playerId = playerId;
        this.price = price;
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.time = Objects.requireNonNull(time, "Time cannot be null.");
    }

    public int getId() {
        return id;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return """
                Description Ticket: {
                Id = %d,
                Room = %d,
                Player = %d,
                Date = %s,
                Time = %s,
                Price = %s
                }
                """.formatted(id, roomId, playerId, date, time, price.setScale(2, RoundingMode.HALF_UP));
    }
}
