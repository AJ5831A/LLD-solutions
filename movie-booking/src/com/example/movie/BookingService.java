package com.example.movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingService {
    private final Map<String, Booking> bookingsById;
    private int sequence;

    public BookingService() {
        this.bookingsById = new HashMap<>();
        this.sequence = 1;
    }

    public Booking createBooking(Show show, List<String> seatIds, String customerName) {
        // validate all seats before modifying any state
        List<Seat> chosenSeats = new ArrayList<>();
        for (String seatId : seatIds) {
            Seat seat = show.findSeat(seatId);
            if (seat == null) {
                throw new IllegalArgumentException("No such seat " + seatId + " in show " + show.getShowId());
            }
            if (seat.isBooked()) {
                throw new IllegalStateException("Seat " + seatId + " is already reserved.");
            }
            chosenSeats.add(seat);
        }

        // mark reserved and tally price
        int totalAmount = 0;
        for (Seat seat : chosenSeats) {
            seat.markBooked();
            totalAmount += seat.getCategory().getPrice();
        }

        String bookingId = "BK-" + (sequence++);
        Booking booking = new Booking(bookingId, customerName, show.getShowId(), chosenSeats, totalAmount);
        bookingsById.put(bookingId, booking);

        System.out.println("Confirmed: " + booking);
        return booking;
    }

    public void cancel(String bookingId) {
        Booking booking = bookingsById.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("No booking found: " + bookingId);
        }
        for (Seat seat : booking.getSeats()) {
            seat.release();
        }
        bookingsById.remove(bookingId);
        System.out.println("Cancelled " + bookingId + " — seats released.");
    }

    public List<Seat> listOpenSeats(Show show) {
        List<Seat> available = new ArrayList<>();
        for (Seat seat : show.getSeats()) {
            if (!seat.isBooked()) {
                available.add(seat);
            }
        }
        return available;
    }
}
