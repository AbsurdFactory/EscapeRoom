package valueobjects;

import exceptions.ValidationException;

public record Id<T>(int value) {

    public Id {
        if (value <= 0) {
            throw new ValidationException("Entity ID must be greater than zero");
        }
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
    public String toString() {
        return String.valueOf(value);
    }
}
