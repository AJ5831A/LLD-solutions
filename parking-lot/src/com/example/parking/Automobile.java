package com.example.parking;

/**
 * Immutable value object holding a vehicle's registration plate and category.
 * @author Aryan Jakhar
 */
public class Automobile {
    private final String plateNumber;
    private final AutoCategory category;

    public Automobile(String plateNumber, AutoCategory category) {
        this.plateNumber = plateNumber;
        this.category = category;
    }

    public String getRegistrationNumber() {
        return plateNumber;
    }

    public AutoCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return category + " [" + plateNumber + "]";
    }
}
