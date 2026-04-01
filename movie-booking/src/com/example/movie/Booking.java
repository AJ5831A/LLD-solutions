package com.example.movie;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private final String id;
    private final String customer;
    private final String showId;
    private final List<Seat> seats;
    private final int totalAmount;

    public Booking(String id, String customer, String showId,
                   List<Seat> seats, int totalAmount) {
        this.id = id;
        this.customer = customer;
        this.showId = showId;
        this.seats = new ArrayList<>(seats);
        this.totalAmount = totalAmount;
    }

    public String getId()         { return id; }
    public String getCustomer()   { return customer; }
    public String getShowId()     { return showId; }
    public List<Seat> getSeats()  { return seats; }
    public int getTotalAmount()   { return totalAmount; }

    @Override
    public String toString() {
        return "Booking{" + id
                + ", customer=" + customer
                + ", show=" + showId
                + ", seats=" + seats
                + ", total=Rs " + totalAmount + "}";
    }
}
