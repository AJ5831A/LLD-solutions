package com.example.parking;

/**
 * Represents size categories for parking bays, each with its own hourly charge.
 * @author Aryan Jakhar
 */
public enum BaySize {
    TWO_WHEELER_BAY(10),
    STANDARD_BAY(20),
    OVERSIZED_BAY(50);

    private final int hourlyRate;

    BaySize(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getPricePerHour() {
        return hourlyRate;
    }
}
