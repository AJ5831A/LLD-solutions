package com.example.parking;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Parking receipt issued upon entry.
 * Holds the booking details and computes charges on exit.
 * @author Aryan Jakhar
 */
public class Receipt {
    private final String ticketId;
    private final Automobile automobile;
    private final Bay allocatedBay;
    private final LocalDateTime arrivalTime;

    public Receipt(String ticketId, Automobile automobile,
                   Bay allocatedBay, LocalDateTime arrivalTime) {
        this.ticketId = ticketId;
        this.automobile = automobile;
        this.allocatedBay = allocatedBay;
        this.arrivalTime = arrivalTime;
    }

    public String getReceiptCode() {
        return ticketId;
    }

    public Automobile getAutomobile() {
        return automobile;
    }

    public Bay getBay() {
        return allocatedBay;
    }

    public LocalDateTime getEntryTime() {
        return arrivalTime;
    }

    /**
     * Calculates the total fee based on the bay's hourly rate
     * and total parking duration.
     */
    public int computeCharges(LocalDateTime departureTime) {
        var hours = Duration.between(arrivalTime, departureTime).toHours();
        if (hours < 1) {
            hours = 1;   // minimum billing of one hour
        }
        var rate = allocatedBay.getSize().getPricePerHour();
        return (int) (hours * rate);
    }

    @Override
    public String toString() {
        return "Receipt#" + ticketId
                + " | " + automobile
                + " | bay=" + allocatedBay.getBayCode()
                + " (" + allocatedBay.getSize() + ")"
                + " | arrived=" + arrivalTime;
    }
}
