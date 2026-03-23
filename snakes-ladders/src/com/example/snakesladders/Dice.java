package com.example.snakesladders;

import java.util.Random;

/**
 * A standard six-sided die (configurable number of faces).
 * @author Aryan Jakhar
 */
public class Dice {
    private final Random rng;
    private final int faces;

    public Dice() {
        this(6);
    }

    public Dice(int faces) {
        this.rng = new Random();
        this.faces = faces;
    }

    /** Rolls the die and returns a value between 1 and {@code faces} inclusive. */
    public int roll() {
        return rng.nextInt(faces) + 1;
    }

    public int getSides() {
        return faces;
    }
}
