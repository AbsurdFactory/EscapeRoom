package menu;

import commonValueObjects.Name;
import commonValueObjects.Price;
import objectdecoration.dao.ObjectDecorationDao;
import objectdecoration.dao.ObjectDecorationDaoImpl;
import objectdecoration.model.Material;
import objectdecoration.model.ObjectDecoration;
import objectdecoration.service.ObjectDecorationService;

import java.util.List;
import java.util.Scanner;

public class ObjectDecorationMenuController extends BaseMenuController {
    private final ObjectDecorationService objectDecorationService;
    private final ObjectDecorationDao objectDecorationDao = new ObjectDecorationDaoImpl();


    public ObjectDecorationMenuController(Scanner scanner) {
        super(scanner);
        this.objectDecorationService = new ObjectDecorationService(objectDecorationDao);
    }

    public void showDecorationMenu() {
        int option;

        do {
            MenuPrinter.displayDecorationMenu();
            option = askOption("Choose an option (1-3): ", 1, 3);

            switch (option) {
                case 1:
                    handleCreateDecoration();
                    break;
                case 2:
                    handleListDecorations();
                    break;
                case 3:
                    System.out.println("\nReturning to previous menu...");
                    break;
            }

        } while (option != 3);
    }


    private void handleCreateDecoration() {
        System.out.println("\n--- Create new Decoration ---");

        try {
            System.out.print("Please enter the decoration name: ");
            Name name = ConsoleInputReader.readName(scanner, "decoration name");

            System.out.print("Please enter the decoration material: ");
            Material material = ConsoleInputReader.readMaterial(scanner, "decoration material");

            System.out.println("Please enter the decoration price.");
            Price price = ConsoleInputReader.readPrice(scanner, "decoration price");


            ObjectDecoration decoration = new ObjectDecoration(null, name, material, price);
            objectDecorationService.addObjectDecoration(decoration);

        } catch (InputReadException e) {
            System.out.println("\nERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected error while creating the Decoration.");
        }
    }


    private void handleListDecorations() {
        System.out.println("\n--- List of Decorations ---");

        try {
            List<ObjectDecoration> decorations = objectDecorationService.getAll();

            if (decorations == null || decorations.isEmpty()) {
                System.out.println("There are no decorations registered yet.");
                return;
            }

            for (ObjectDecoration decoration : decorations) {
                System.out.println("------------------------------");
                System.out.println("Name: " + decoration.getName());
                System.out.println("Material: " + decoration.getMaterial());
                System.out.println("Price: " + decoration.getPrice());
            }
            System.out.println("------------------------------");

        } catch (Exception e) {
            System.out.println("\nERROR retrieving Decorations list.");
        }
    }

    @Override
    public void showMenu() throws InputReadException {
        showDecorationMenu();
    }
}

