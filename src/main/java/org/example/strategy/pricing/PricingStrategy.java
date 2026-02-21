package org.example.strategy.pricing;

public interface PricingStrategy {
    double calculateFine(long overdueDays);
}
