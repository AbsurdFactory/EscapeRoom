package clue.model;

public record ClueName(String name) {

    public ClueName {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() > 45 ){
            throw new IllegalArgumentException("Name cannot be superior to 45 characters");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
