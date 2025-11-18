package validators;

import exceptions.ValidationException;
import objectdecoration.model.ObjectDecoration;

public class ObjectDecorationValidator {

    private ObjectDecorationValidator() {
    }

    public static void validate(ObjectDecoration decoration) {
        if (decoration == null) {
            throw new ValidationException("Decoration cannot be null.");
        }

        if (isBlank(decoration.getName())) {
            throw new ValidationException("Decoration name is required.");
        }

        if (isBlank(decoration.getMaterial())) {
            throw new ValidationException("Material is required.");
        }

        if (decoration.getPrice() < 0) {
            throw new ValidationException("Price cannot be negative.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}