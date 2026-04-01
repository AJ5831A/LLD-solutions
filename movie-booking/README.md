# Movie Ticket Booking System

## Class Diagram

```
+---------------------+
|      <<enum>>       |
|    SeatCategory     |
|---------------------|
| STANDARD (Rs 180)   |
| PREMIUM  (Rs 280)   |
| VIP      (Rs 450)   |
|---------------------|
| +getPrice(): int    |
+---------------------+
          |
          | has-a
          v
+---------------------+        +---------------------+
|        Seat         |        |       Movie         |
|---------------------|        |---------------------|
| -id: String         |        | -title: String      |
| -row: int           |        | -lengthMinutes: int |
| -col: int           |        +---------------------+
| -category           |                  |
| -booked: boolean    |                  |
|---------------------|                  |
| +markBooked()       |                  |
| +release()          |                  |
| +isBooked()         |                  |
+---------------------+                  |
          |                              |
          +-------------+   +-----------+
                        v   v
                 +---------------------+
                 |        Show         |
                 |---------------------|
                 | -showId: String     |
                 | -movie: Movie       |
                 | -hall: String       |
                 | -timing             |
                 | -seats: List<Seat>  |
                 |---------------------|
| +findSeat(id)       |
                 +---------------------+
                         |
                         | used by
                         v
+------------------------------------------------+
|               BookingService                   |
|------------------------------------------------|
| -bookingsById: Map<String, Booking>            |
| -sequence: int                                 |
|------------------------------------------------|
| +createBooking(show, seatIds, customer): Booking|
| +cancel(bookingId): void                       |
| +listOpenSeats(show): List<Seat>               |
+------------------------------------------------+
                         |
                         | creates
                         v
+------------------------------------------------+
|                  Booking                       |
|------------------------------------------------|
| -id: String                                    |
| -customer: String                              |
| -showId: String                                |
| -seats: List<Seat>                             |
| -totalAmount: int                              |
+------------------------------------------------+
```

## Design Approach

### Entities
`Movie` carries metadata (title, duration). `Show` represents a particular screening (hall + time) and owns its seat layout. `Seat` is mutable — it toggles between booked and available. `Booking` is an immutable receipt for a successful reservation.

### Booking Flow
`BookingService.createBooking()` follows a simple two-phase flow:
1. Validate every requested seat exists and is not already booked (fails atomically if any seat is taken).
2. Mark all seats as booked and compute the total based on each seat’s category price.

This guarantees no partial reservations — either all requested seats are booked or none are.

### Cancellation
`cancel()` releases all seats tied to a booking and removes the booking record, making those seats available again.

### Pricing
Each `Seat` carries a `SeatCategory` (STANDARD / PREMIUM / VIP) with a fixed price. A booking's total is the sum of its constituent seat prices.

## How to Build and Run

```bash
cd movie-booking
javac -d out src/com/example/movie/*.java
java -cp out com.example.movie.App
```
