package room.model;

public record DifficultyLevel(String value) {

    private static final String EASY = "Easy";
    private static final String MEDIUM = "Medium";
    private static final String HARD = "Hard";

    public DifficultyLevel {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Difficulty level cannot be empty");
        }

        String normalized = value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();

        if (!normalized.equals(EASY) && !normalized.equals(MEDIUM) && !normalized.equals(HARD)) {
            throw new IllegalArgumentException(
                    "Difficulty level must be 'Easy', 'Medium', or 'Hard'. Received: " + value
            );
        }

        value = normalized;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isEasy() {
        return EASY.equals(value);
    }

    public boolean isMedium() {
        return MEDIUM.equals(value);
    }

    public boolean isHard() {
        return HARD.equals(value);
    }
}
