package com.example.parking;

/**
 * Simple wrapper around a gate identifier.
 * @author Aryan Jakhar
 */
public class Entrance {
    private final String gateId;

    public Entrance(String gateId) {
        this.gateId = gateId;
    }

    public String getEntranceId() {
        return gateId;
    }

    @Override
    public String toString() {
        return "Entrance-" + gateId;
    }
}
