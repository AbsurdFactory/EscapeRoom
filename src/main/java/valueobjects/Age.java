package valueobjects;

import exceptions.ValidationException;

public class Age {
    private final int value;

    public Age(int value) {
        if (value <= 0) {
            throw new ValidationException("Age must be greater than zero");
        }
        if (value < 12) {
            throw new ValidationException("Player must be at least 12 years old to register");
        }
        if (value > 120) {
            throw new ValidationException("Invalid age: must be less than 120");
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
        if (!(o instanceof Age age)) return false;
        return value == age.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
