package menu;

import clue.dao.ClueDaoImplementation;
import clue.model.Clue;
import clue.model.ClueText;
import clue.model.ClueTheme;
import clue.service.ClueService;
import commonValueObjects.Name;
import commonValueObjects.Price;

import java.util.List;
import java.util.Scanner;

public class ClueMenuController extends BaseMenuController {

    private final ClueService clueService;
    private final ClueDaoImplementation clueDaoImplementation = new ClueDaoImplementation();

    public ClueMenuController(Scanner scanner) {
        super(scanner);
        this.clueService = new ClueService(clueDaoImplementation);
    }

    public void showClueMenu() {
        int option;

        do {
            MenuPrinter.displayClueMenu();
            option = askOption("Choose an option (1-3): ", 1, 3);

            switch (option) {
                case 1:
                    handleCreateClue();
                    break;
                case 2:
                    handleListClues();
                    break;
                case 3:
                    System.out.println("\nReturning to previous menu...");
                    break;
            }

        } while (option != 3);
    }



    private void handleCreateClue() {
        System.out.println("\n--- Create new Clue ---");

        try {
            System.out.println("Please enter the name of the clue.");
            Name clueName = ConsoleInputReader.readName(scanner, "clue name");
            System.out.println("Please enter the text description for the clue.");
            ClueText clueText = ConsoleInputReader.readClueText(scanner, "text description");
            System.out.println("Please enter the theme of the clue.");
            ClueTheme clueTheme = ConsoleInputReader.readClueTheme(scanner, "theme");
            Price cluePrice = ConsoleInputReader.readPrice(scanner, "price");
            System.out.println("Please enter the clue's price.");
            cluePrice = ConsoleInputReader.readPrice(scanner, "price");

            Clue clue = new Clue(clueName, clueText, clueTheme, cluePrice);
            clueService.createClue(clue);


        } catch (InputReadException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected error while creating the Clue.");
        }
    }



    private void handleListClues() {
        System.out.println("\n--- List of Clues ---");

        try {
            List<Clue> clues = clueService.getAllClues();

            if (clues == null || clues.isEmpty()) {
                System.out.println("There are no clues registered yet.");
                return;
            }
            for (Clue clue : clues) {
                System.out.println("------------------------------");

                System.out.println("Name: " + clue.getName());
                System.out.println("Description: " + clue.getText());
                System.out.println("Theme: " + clue.getTheme());
                System.out.println("Price: " + clue.getPrice());
            }
            System.out.println("------------------------------");

        } catch (Exception e) {
            System.out.println("\nERROR retrieving Clues list.");
        }
    }


    @Override
    public void showMenu() {
        showClueMenu();
    }
}
