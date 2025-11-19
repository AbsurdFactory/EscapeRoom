package validators;

import exceptions.ValidationException;
import room.model.Room;



public class RoomValidator {
    private RoomValidator(){}

    public static void validateRoom(Room room){
        if (room == null) {
            throw new ValidationException("The room cannot be null.");
        }

        if (room.getName().isBlank()) {
            throw new ValidationException("The room name is required.");
        }

        if (room.getPrice() < 0) {
            throw new ValidationException("The price cannot be negative.");
        }

        if (room.getDifficultyLevel().isBlank() || room.getDifficultyLevel().isEmpty()) {
            throw new ValidationException("The difficulty level must be greater than 0.");
        }
    }

}
