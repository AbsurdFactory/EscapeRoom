package clue.model;

public record ClueTheme(String theme) {
    public ClueTheme {
        if (theme == null || theme.isBlank()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }

        if (theme.length() > 45) {
            throw new IllegalArgumentException("Text cannot be superior to 45 characters");
        }
    }

    @Override
    public String toString() {
        return theme;
    }
}
