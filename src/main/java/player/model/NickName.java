package player.model;

import exceptions.ValidationException;

import java.util.Objects;

public record NickName (String value){

    public NickName(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException("NickName cannot be empty");
        }
        if (value.length() < 3 || value.length() > 45) {
            throw new ValidationException("NickName must be between 3 and 45 characters");
        }
        this.value = value.trim();
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
        if (!(o instanceof NickName nickName)) return false;
        return value.equalsIgnoreCase(nickName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }
}
