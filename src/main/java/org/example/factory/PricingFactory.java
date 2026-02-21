package org.example.factory;


import org.example.strategy.pricing.DayWisePricingStrategy;
import org.example.strategy.pricing.PricingStrategy;
import org.example.strategy.pricing.WeeklyPricingStrategy;

public class PricingFactory {
    public static PricingStrategy getPricingStrategy(long overdueDays) {
        if (overdueDays >= 7) return new WeeklyPricingStrategy();
        return new DayWisePricingStrategy();
    }
}

