package com.example.parking;

import java.util.Map;
import java.util.TreeMap;

/**
 * A single parking slot with a fixed size category, floor level,
 * and distance map to each entrance gate.
 * @author Aryan Jakhar
 */
public class Bay {
    private final String bayCode;
    private final BaySize size;
    private final int floor;
    private final TreeMap<String, Integer> distanceMap;
    private boolean occupied;

    public Bay(String bayCode, BaySize size, int level,
               Map<String, Integer> proximityMap) {
        this.bayCode = bayCode;
        this.size = size;
        this.floor = level;
        this.distanceMap = new TreeMap<>(proximityMap);
        this.occupied = false;
    }

    public String getBayCode() {
        return bayCode;
    }

    public BaySize getSize() {
        return size;
    }

    public int getLevel() {
        return floor;
    }

    public boolean isTaken() {
        return occupied;
    }

    /** Returns the walking distance from the given gate to this bay. */
    public int proximityTo(String gateId) {
        return distanceMap.getOrDefault(gateId, Integer.MAX_VALUE);
    }

    public void markInUse() {
        this.occupied = true;
    }

    public void markFree() {
        this.occupied = false;
    }

    @Override
    public String toString() {
        return bayCode + " [size=" + size + ", floor=" + floor + ", occupied=" + occupied + "]";
    }
}
