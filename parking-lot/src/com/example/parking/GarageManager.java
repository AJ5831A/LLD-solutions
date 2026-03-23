package com.example.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Central controller that handles vehicle assignment,
 * fee computation, and bay availability queries.
 * @author Aryan Jakhar
 */
public class GarageManager {
    private final List<Bay> allBays;
    private int ticketCounter = 0;

    public GarageManager(List<Bay> bays) {
        this.allBays = new ArrayList<>(bays);
    }

    /**
     * Locates the nearest compatible bay from the given entrance,
     * marks it occupied, and returns a parking receipt.
     */
    public Receipt parkVehicle(Automobile automobile, LocalDateTime entryTime,
                               BaySize requestedSize, String entranceId) {
        var chosen = locateClosestBay(automobile, requestedSize, entranceId);

        if (chosen == null) {
            throw new IllegalStateException("No vacant " + requestedSize
                    + " bay available for " + automobile);
        }

        chosen.markInUse();
        ticketCounter++;
        var id = "R-" + ticketCounter;
        return new Receipt(id, automobile, chosen, entryTime);
    }

    /** Calculates the total fee, releases the bay, and returns the amount due. */
    public int processExit(Receipt receipt, LocalDateTime exitTime) {
        var amount = receipt.computeCharges(exitTime);
        receipt.getBay().markFree();
        return amount;
    }

    /** Reports how many bays of each size category are currently vacant. */
    public Map<BaySize, Long> availability() {
        return allBays.stream()
                .filter(b -> !b.isTaken())
                .collect(Collectors.groupingBy(Bay::getSize, Collectors.counting()));
    }

    /* Scans all bays to find the nearest vacant one that matches the requested size. */
    private Bay locateClosestBay(Automobile automobile, BaySize requestedSize, String entranceId) {
        return allBays.stream()
                .filter(b -> !b.isTaken())
                .filter(b -> b.getSize() == requestedSize)
                .filter(b -> automobile.getCategory().canAccommodate(b.getSize()))
                .min(Comparator.comparingInt(b -> b.proximityTo(entranceId)))
                .orElse(null);
    }
}
