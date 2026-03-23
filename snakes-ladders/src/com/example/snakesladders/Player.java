package com.example.snakesladders;

/**
 * Tracks a single participant's name, board position, rank, and completion status.
 * @author Aryan Jakhar
 */
public class Player {
    private final String alias;
    private int currentCell;
    private int finalRank;
    private boolean done;

    public Player(String alias) {
        this.alias = alias;
        this.currentCell = 0;
        this.finalRank = 0;
        this.done = false;
    }

    public String getName() {
        return alias;
    }

    public int getPosition() {
        return currentCell;
    }

    public int getRank() {
        return finalRank;
    }

    public boolean isFinished() {
        return done;
    }

    public void moveTo(int cell) {
        this.currentCell = cell;
    }

    public void setRank(int rank) {
        this.finalRank = rank;
    }

    public void setFinished(boolean completed) {
        this.done = completed;
    }

    @Override
    public String toString() {
        return alias + " [cell=" + currentCell + "]";
    }
}
