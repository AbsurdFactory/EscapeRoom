package menu;

import java.util.Scanner;

public abstract class BaseMenuController {
    protected final Scanner scanner;

    protected BaseMenuController(Scanner scanner) {
        this.scanner = scanner;
    }

    protected int askOption(String message, int min, int max) throws InputReadException {
        while (true) {
            try {
                String line = scanner.nextLine();
                int option = Integer.parseInt(line.trim());

                if (option < min || option > max) {
                    System.out.printf("Please enter a number between %d and %d.%n", min, max);
                    continue;
                }

                return option;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }


    public abstract void showMenu() throws InputReadException;
}
