package commonValueObjects;

import exceptions.ValidationException;

import java.util.Objects;

public final class Id<T> {

    private final int value;

    public Id(int value) {
        if (value <= 0) {
            throw new ValidationException("Entity ID must be greater than zero");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean sameValueAs(Id<T> other) {
        return other != null && this.value == other.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Id<?> other)) return false;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
