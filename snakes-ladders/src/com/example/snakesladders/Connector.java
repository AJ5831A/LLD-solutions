package com.example.snakesladders;

/**
 * Represents a link between two cells on the board (either a snake or a ladder).
 * @author Aryan Jakhar
 */
public class Connector {
    private final int startCell;
    private final int endCell;

    public Connector(int startCell, int endCell) {
        this.startCell = startCell;
        this.endCell = endCell;
    }

    public int getOrigin() {
        return startCell;
    }

    public int getTarget() {
        return endCell;
    }

    /** A connector is a snake when it moves the player backwards. */
    public boolean isSnake() {
        return endCell < startCell;
    }

    @Override
    public String toString() {
        var kind = isSnake() ? "Snake" : "Ladder";
        return kind + "(" + startCell + " => " + endCell + ")";
    }
}
