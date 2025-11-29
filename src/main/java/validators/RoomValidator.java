package validators;

import exceptions.ValidationException;
import room.model.Room;



public class RoomValidator {
    private RoomValidator(){}

    public static void validateRoom(Room room){
        if (room == null) {
            throw new ValidationException("The room cannot be null.");
        }

    }

}
