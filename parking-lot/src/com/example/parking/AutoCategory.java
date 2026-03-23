package com.example.parking;

/**
 * Vehicle classification used to determine bay compatibility.
 * @author Aryan Jakhar
 */
public enum AutoCategory {
    TWO_WHEELER,
    CAR,
    BUS;

    /**
     * Checks whether a vehicle of this category fits into the supplied bay size.
     * Ordinal comparison: TWO_WHEELER(0) fits in any bay, CAR(1) needs STANDARD or larger,
     * BUS(2) requires OVERSIZED only.
     */
    public boolean canAccommodate(BaySize baySize) {
        return this.ordinal() <= baySize.ordinal();
    }
}
