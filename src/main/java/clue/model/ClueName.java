package clue.model;

public record ClueName(String value) {
    public ClueName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }
}
