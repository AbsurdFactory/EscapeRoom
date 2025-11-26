package clue.model;

public record ClueText(String text) {

    public ClueText {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }

        if (text.length() > 250 ){
            throw new IllegalArgumentException("Text cannot be superior to 250 characters");
        }
    }

    @Override
    public String toString() {
        return text;
    }
}
