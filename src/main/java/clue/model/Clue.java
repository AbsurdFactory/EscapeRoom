package clue.model;

import java.util.Objects;

public class Clue {
    private final ClueName name;
    private ClueText text;
    private ClueTheme theme;
    private CluePrice price;

    public Clue(ClueName name) {
        this.name = Objects.requireNonNull(name, "The name must be informed");
    }

    public Clue(ClueName name, ClueText text, ClueTheme theme, CluePrice price) {

        this.name = Objects.requireNonNull(name, "The name must be informed");
        this.text = Objects.requireNonNull(text, "The action must be indicated");
        this.theme = Objects.requireNonNull(theme, "The theme must be indicated");
        this.price = price;
    }

    public Clue(String name, String text, String theme, double price) {

        this.name = new ClueName(name);
        this.text = new ClueText(text);
        this.theme = new ClueTheme(theme);
        this.price = new CluePrice(price);
    }

    public ClueName getName() {
        return name;
    }

    public ClueText getText() {
        return text;
    }

    public ClueTheme getTheme() {
        return theme;
    }

    public CluePrice getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Clue = " + name.toString() +
                ", Action = " + text.toString() +
                ", Theme = " + theme.toString() +
                ", Price = " + price.toDouble();

    }
}
