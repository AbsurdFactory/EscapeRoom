package valueobjects;

public class Price {
    private final double value;

    public Price(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
