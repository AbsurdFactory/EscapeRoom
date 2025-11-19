package player.valueobjects;

import exceptions.ValidationException;

import java.util.Objects;

public class PlayerId {
    private final int value;

    public PlayerId(int value) {
        if (value <= 0) {
            throw new ValidationException("Player ID must be greater than zero");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerId)) return false;
        PlayerId playerId = (PlayerId) o;
        return value == playerId.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
