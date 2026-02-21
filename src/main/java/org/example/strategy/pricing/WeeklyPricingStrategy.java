package org.example.strategy.pricing;

import org.example.config.LibraryConfig;

public class WeeklyPricingStrategy implements PricingStrategy {
    @Override
    public double calculateFine(long overdueDays) {
        long weeks = (overdueDays / 7) + 1;
        return weeks * LibraryConfig.FINE_PER_WEEK;
    }
}

