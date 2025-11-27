package valueobjects;

public class Name {
    private final String value;

    public Name(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Name too long");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
