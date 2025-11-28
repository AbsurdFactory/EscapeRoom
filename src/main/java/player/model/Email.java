package player.model;

import exceptions.ValidationException;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email (String value){
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new ValidationException("Invalid email format");
        }
        this.value = value.trim().toLowerCase();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
