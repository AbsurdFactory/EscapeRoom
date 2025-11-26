package clue.model;

public record ClueName(String name) {

    public ClueName {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
