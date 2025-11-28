package commonValueObjects;

import java.math.BigDecimal;

public record Price (BigDecimal price) {
public Price {

    if (price == null) {
        throw new IllegalArgumentException("Price cannot be empty");
    }

    if (price.compareTo(BigDecimal.ZERO) < 0) {
        throw new RuntimeException("The price can't be negative");
    }

}

public BigDecimal toBigDecimal() {
    return price;
}
}

