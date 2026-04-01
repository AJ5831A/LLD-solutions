package com.example.movie;

public class Seat {
    private final String id;
    private final int row;
    private final int col;
    private final SeatCategory category;
    private boolean booked;

    public Seat(String id, int row, int col, SeatCategory category) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.category = category;
        this.booked = false;
    }

    public String getId()             { return id; }
    public int getRow()               { return row; }
    public int getCol()               { return col; }
    public SeatCategory getCategory() { return category; }
    public boolean isBooked()         { return booked; }

    public void markBooked() { this.booked = true; }
    public void release()    { this.booked = false; }

    @Override
    public String toString() {
        return id + "[" + category + " Rs" + category.getPrice()
                + " " + (booked ? "taken" : "open") + "]";
    }
}
