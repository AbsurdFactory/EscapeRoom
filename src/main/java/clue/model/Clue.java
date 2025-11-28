package clue.model;

import commonValueObjects.Name;
import commonValueObjects.Price;

import java.math.BigDecimal;
import java.util.Objects;

public class Clue {
    private final Name name;
    private ClueText text;
    private ClueTheme theme;
    private Price price;

    public Clue(Name name) {
        this.name = Objects.requireNonNull(name, "The name must be informed");
    }

    public Clue(Name name, ClueText text, ClueTheme theme, Price price) {

        this.name = Objects.requireNonNull(name, "The name must be informed");
        this.text = Objects.requireNonNull(text, "The action must be indicated");
        this.theme = Objects.requireNonNull(theme, "The theme must be indicated");
        this.price = price;
    }

    public Clue(String name, String text, String theme, BigDecimal price) {

        this.name = new Name(name);
        this.text = new ClueText(text);
        this.theme = new ClueTheme(theme);
        this.price = new Price(price);
    }

    public Name getName() {
        return name;
    }

    public ClueText getText() {
        return text;
    }

    public ClueTheme getTheme() {
        return theme;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Clue = " + name.toString() +
                ", Action = " + text.toString() +
                ", Theme = " + theme.toString() +
                ", Price = " + price.toBigDecimal();

    }
}
