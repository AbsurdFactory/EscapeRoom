package clue.model;

public record CluePrice(Double price) {
    public CluePrice {

        if (price == null) {
            throw new IllegalArgumentException("Price cannot be empty");
        }

        if (price <= 0) {
            throw new RuntimeException("The price can't be negative or zero");
        }

    }

    public Double toDouble() {
        return price;
    }
}
