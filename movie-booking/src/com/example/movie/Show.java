package com.example.movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private final String showId;
    private final Movie movie;
    private final String hall;
    private final LocalDateTime timing;
    private final List<Seat> seats;

    public Show(String showId, Movie movie, String hall,
                LocalDateTime timing, List<Seat> seats) {
        this.showId = showId;
        this.movie = movie;
        this.hall = hall;
        this.timing = timing;
        this.seats = new ArrayList<>(seats);
    }

    public String getShowId()        { return showId; }
    public Movie getMovie()          { return movie; }
    public String getHall()          { return hall; }
    public LocalDateTime getTiming() { return timing; }
    public List<Seat> getSeats()     { return seats; }

    public Seat findSeat(String seatId) {
        for (Seat seat : seats) {
            if (seat.getId().equals(seatId)) {
                return seat;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return showId + " | " + movie.getTitle() + " | " + hall + " | " + timing;
    }
}
