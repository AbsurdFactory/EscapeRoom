package validators;

import clue.model.Clue;
import exceptions.ValidationException;
import objectdecoration.model.ObjectDecoration;

public class ClueValidator {

    private ClueValidator() {
    }

    public static void validate(Clue clue) {
        if (clue == null) {
            throw new ValidationException("Clue cannot be null.");
        }

        if (isBlank(clue.getName())) {
            throw new ValidationException("Name name is required.");
        }

        if (isBlank(clue.getText())) {
            throw new ValidationException("Text is required.");
        }

        if (isBlank(clue.getTheme())) {
            throw new ValidationException("Theme is required.");
        }

        if (clue.getPrice() < 0) {
            throw new ValidationException("Price cannot be negative.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}