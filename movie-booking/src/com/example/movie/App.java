package com.example.movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Movie movie1 = new Movie("The Dark Knight", 152);
        Movie movie2 = new Movie("Dune: Part Two", 166);

        // hall 1 layout: rows 1-3, varying categories
        List<Seat> hall1 = new ArrayList<>();
        hall1.add(new Seat("R1", 1, 1, SeatCategory.STANDARD));
        hall1.add(new Seat("R2", 1, 2, SeatCategory.STANDARD));
        hall1.add(new Seat("R3", 1, 3, SeatCategory.STANDARD));
        hall1.add(new Seat("P1", 2, 1, SeatCategory.PREMIUM));
        hall1.add(new Seat("P2", 2, 2, SeatCategory.PREMIUM));
        hall1.add(new Seat("V1", 3, 1, SeatCategory.VIP));
        hall1.add(new Seat("V2", 3, 2, SeatCategory.VIP));

        Show show1 = new Show("SH1", movie1, "Audi-1",
                LocalDateTime.of(2025, 7, 15, 19, 30), hall1);
        Show show2 = new Show("SH2", movie2, "Audi-2",
                LocalDateTime.of(2025, 7, 15, 21, 0), createHall2());

        BookingService bookingService = new BookingService();

        System.out.println("=== Movie Ticket Booking ===\n");
        System.out.println("Show: " + show1);
        System.out.println("Open seats: " + bookingService.listOpenSeats(show1));

        // Ravi books R1 and P1
        System.out.println("\n-- Ravi books R1, P1 --");
        Booking raviBooking = bookingService.createBooking(show1, Arrays.asList("R1", "P1"), "Ravi");

        // Sneha books V1
        System.out.println("\n-- Sneha books V1 --");
        Booking snehaBooking = bookingService.createBooking(show1, Arrays.asList("V1"), "Sneha");

        System.out.println("\nOpen seats now: " + bookingService.listOpenSeats(show1));

        // Amit tries to book R1 (taken)
        System.out.println("\n-- Amit tries R1 (already taken) --");
        try {
            bookingService.createBooking(show1, Arrays.asList("R1"), "Amit");
        } catch (IllegalStateException ex) {
            System.out.println("Rejected: " + ex.getMessage());
        }

        // Ravi cancels
        System.out.println("\n-- Ravi cancels " + raviBooking.getId() + " --");
        bookingService.cancel(raviBooking.getId());
        System.out.println("Open seats after cancel: " + bookingService.listOpenSeats(show1));

        // Amit rebooks R1
        System.out.println("\n-- Amit books R1 after cancellation --");
        bookingService.createBooking(show1, Arrays.asList("R1"), "Amit");

        // second show booking
        System.out.println("\n-- Show 2: " + show2 + " --");
        bookingService.createBooking(show2, Arrays.asList("X1", "X2"), "Priya");
    }

    private static List<Seat> createHall2() {
        List<Seat> seats = new ArrayList<>();
        seats.add(new Seat("X1", 1, 1, SeatCategory.STANDARD));
        seats.add(new Seat("X2", 1, 2, SeatCategory.STANDARD));
        seats.add(new Seat("Y1", 2, 1, SeatCategory.PREMIUM));
        seats.add(new Seat("Z1", 3, 1, SeatCategory.VIP));
        return seats;
    }
}
