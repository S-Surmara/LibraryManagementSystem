package org.example.service;


import org.example.factory.PricingFactory;
import org.example.strategy.pricing.PricingStrategy;

public class FineCalculator {
    public double calculate(long overdueDays) {
        PricingStrategy strategy = PricingFactory.getPricingStrategy(overdueDays);
        return strategy.calculateFine(overdueDays);
    }
}

