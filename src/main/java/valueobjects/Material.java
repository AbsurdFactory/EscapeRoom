package valueobjects;

public class Material {
    private final String value;

    public Material(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Material cannot be empty");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
