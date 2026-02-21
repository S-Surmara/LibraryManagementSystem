package org.example.strategy.pricing;
import org.example.config.LibraryConfig;

public class DayWisePricingStrategy implements PricingStrategy {
    @Override
    public double calculateFine(long overdueDays) {
        return overdueDays * LibraryConfig.FINE_PER_DAY;
    }
}

