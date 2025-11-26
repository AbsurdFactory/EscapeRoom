package clue.model;

import java.util.Objects;

public class Clue {
    ClueName name;
    String text;
    String theme;
    double price;

    public Clue(ClueName name) {
        this.name = Objects.requireNonNull(name, "The name must be informed");
    }

    public Clue(ClueName name, String text, String theme, double price) {
        if (price <= 0) {
            throw new RuntimeException("The price can't be negative or zero");
        }
        this.name = Objects.requireNonNull(name, "The name must be informed");
        this.text = Objects.requireNonNull(text, "The action must be indicated");
        this.theme = Objects.requireNonNull(theme, "The theme must be indicated");
        this.price = price;
    }

    public ClueName getNameValue() {
        return name;
    }
    public String getName() {
        return name.toString();
    }
    public String getText() {
        return text;
    }

    public String getTheme() {
        return theme;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Clue = " + name +
                ", Action = " + text +
                ", Theme = "+ theme +
                ", Price = "+ price ;

    }
}
