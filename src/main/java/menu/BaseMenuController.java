package menu;

import java.util.Scanner;

public abstract class BaseMenuController {
    protected final Scanner scanner;

    protected BaseMenuController(Scanner scanner) {
        this.scanner = scanner;
    }

    protected int askOption(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = ConsoleInputReader.readInt(scanner);
                if (value < min || value > max) {
                    System.out.println("\nERROR: Please choose a number between " + min + " and " + max + ".\n");
                    continue;
                }
                return value;
            } catch (InputReadException e) {
                System.out.println("\nERROR: " + e.getMessage() + "\n");
            }
        }
    }


    public abstract void showMenu() throws InputReadException;
}
