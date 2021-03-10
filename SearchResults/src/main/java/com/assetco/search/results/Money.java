package com.assetco.search.results;

import java.math.*;
import java.util.*;

public class Money {
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return Currency.getInstance(Locale.US);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
