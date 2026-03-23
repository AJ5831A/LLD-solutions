package com.example.snakesladders;

/**
 * Configures how aggressively snakes and ladders are placed on the board.
 * Percentage values are relative to total board cells.
 * @author Aryan Jakhar
 */
public enum DifficultyLevel {
    //                  snakeMin%, snakeMax%, ladderMin%, ladderMax%
    BEGINNER(           1,         5,         5,          15),
    EXPERT(             10,        20,        1,          5);

    private final int snakeMinDrop;
    private final int snakeMaxDrop;
    private final int ladderMinClimb;
    private final int ladderMaxClimb;

    DifficultyLevel(int snakeMinDrop, int snakeMaxDrop,
                    int ladderMinClimb, int ladderMaxClimb) {
        this.snakeMinDrop = snakeMinDrop;
        this.snakeMaxDrop = snakeMaxDrop;
        this.ladderMinClimb = ladderMinClimb;
        this.ladderMaxClimb = ladderMaxClimb;
    }

    public int getSnakeMinDrop()   { return snakeMinDrop; }
    public int getSnakeMaxDrop()   { return snakeMaxDrop; }
    public int getLadderMinClimb() { return ladderMinClimb; }
    public int getLadderMaxClimb() { return ladderMaxClimb; }

    /** Maps user-friendly labels ("easy"/"hard") to enum constants. */
    public static DifficultyLevel fromInput(String input) {
        var upper = input.trim().toUpperCase();
        if (upper.equals("EASY")) {
            return BEGINNER;
        } else if (upper.equals("HARD")) {
            return EXPERT;
        }
        return valueOf(upper);
    }
}
