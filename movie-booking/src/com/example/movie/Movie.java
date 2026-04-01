package com.example.movie;

public class Movie {
    private final String title;
    private final int lengthMinutes;

    public Movie(String title, int lengthMinutes) {
        this.title = title;
        this.lengthMinutes = lengthMinutes;
    }

    public String getTitle()      { return title; }
    public int getLengthMinutes() { return lengthMinutes; }

    @Override
    public String toString() {
        return title + " (" + lengthMinutes + " min)";
    }
}
