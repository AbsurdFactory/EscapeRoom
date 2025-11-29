package menu;

import clue.model.ClueText;
import clue.model.ClueTheme;
import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.model.Material;
import room.model.DifficultyLevel;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleInputReader {
    public static int readInt(Scanner scanner) throws InputReadException {
        try {
            int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new InputReadException("Please enter a valid number.");
        }
    }

    public static BigDecimal readBigDecimal(Scanner scanner, String fieldName) throws InputReadException {
        try {
            BigDecimal value = scanner.nextBigDecimal();
            scanner.nextLine();
            return value;
        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new InputReadException("Please enter a valid decimal number for " + fieldName + ".");
        }
    }

    public static String readNonEmptyString(Scanner scanner, String fieldName) throws InputReadException {
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            throw new InputReadException("The field '" + fieldName + "' cannot be empty.");
        }
        return value;
    }

    public static boolean readBoolean(Scanner scanner, String fieldName) throws InputReadException {
        String value = scanner.nextLine().trim().toLowerCase();
        if ("true".equals(value) || "yes".equals(value) || "y".equals(value)) {
            return true;
        }
        if ("false".equals(value) || "no".equals(value) || "n".equals(value)) {
            return false;
        }
        throw new InputReadException("Please enter 'true' or 'false' for " + fieldName + ".");
    }
    public static Price readPrice(Scanner scanner, String fieldName) throws InputReadException {
        try {
            BigDecimal value = scanner.nextBigDecimal();
            scanner.nextLine();


            return new Price(value);

        } catch (InputMismatchException e) {
            scanner.nextLine();
            throw new InputReadException("Please enter a valid decimal number for " + fieldName + ".");
        }

    }

    public static Name readName(Scanner scanner, String fieldName) throws InputReadException {
        while (true) {
            try {
                String value = scanner.nextLine().trim();
                return new Name(value);
            } catch (IllegalArgumentException e) {
                throw new InputReadException(fieldName + ": " + e.getMessage());
            }
        }
    }

    public static ClueText readClueText(Scanner scanner, String fieldName) throws InputReadException {
        try {
            String value = scanner.nextLine().trim();
            return new ClueText(value);
        } catch (IllegalArgumentException e) {
            throw new InputReadException(fieldName + ": " + e.getMessage());
        }
    }

    public static ClueTheme readClueTheme(Scanner scanner, String fieldName) throws InputReadException {
        try {
            String value = scanner.nextLine().trim();
            return new ClueTheme(value);
        } catch (IllegalArgumentException e) {
            throw new InputReadException(fieldName + ": " + e.getMessage());
        }
    }

    public static Material readMaterial(Scanner scanner, String fieldName) throws InputReadException {
        try {
            String value = scanner.nextLine().trim();
            return new Material(value);
        } catch (IllegalArgumentException e) {
            throw new InputReadException(fieldName + ": " + e.getMessage());
        }
    }

    public static DifficultyLevel readDifficultyLevel(Scanner scanner, String fieldName) throws InputReadException {
        try {
            String value = scanner.nextLine().trim();
            return new DifficultyLevel(value);
        } catch (IllegalArgumentException e) {
            throw new InputReadException(fieldName + ": " + e.getMessage());
        }
    }


}
