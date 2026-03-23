package com.example.pen;

/**
 * Represents a refillable ink cartridge that tracks remaining ink volume.
 * @author Aryan Jakhar
 */
public class InkCartridge {

    private final Shade shade;
    private final int maxVolume;
    private int remainingInk;

    public InkCartridge(Shade shade, int capacity) {
        this.shade = shade;
        this.maxVolume = capacity;
        this.remainingInk = capacity;
    }

    public Shade getShade() {
        return shade;
    }

    public int getCurrentInk() {
        return remainingInk;
    }

    public int getCapacity() {
        return maxVolume;
    }

    /** Checks whether all ink has been consumed. */
    public boolean isDepleted() {
        return remainingInk <= 0;
    }

    /**
     * Consumes the requested amount of ink (or whatever is left)
     * and returns how many units were actually consumed.
     */
    public int useInk(int requested) {
        var usable = Math.min(requested, remainingInk);
        remainingInk -= usable;
        return usable;
    }

    /** Refills the cartridge back to its maximum volume. */
    public void restore() {
        remainingInk = maxVolume;
    }

    @Override
    public String toString() {
        return shade + " cartridge (" + remainingInk + "/" + maxVolume + ")";
    }
}
