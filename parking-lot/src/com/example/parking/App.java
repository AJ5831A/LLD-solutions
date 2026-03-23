package com.example.parking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entry-point demonstrating the multilevel garage system.
 * @author Aryan Jakhar
 */
public class App {
    public static void main(String[] args) {
        var slots = new ArrayList<Bay>();

        // floor 1 – small and medium bays
        slots.add(buildBay("S-1", BaySize.TWO_WHEELER_BAY, 1, 10, 50));
        slots.add(buildBay("S-2", BaySize.TWO_WHEELER_BAY, 1, 20, 40));
        slots.add(buildBay("M-1", BaySize.STANDARD_BAY, 1, 15, 45));
        slots.add(buildBay("M-2", BaySize.STANDARD_BAY, 1, 25, 35));

        // floor 2 – medium and large bays
        slots.add(buildBay("M-3", BaySize.STANDARD_BAY, 2, 30, 20));
        slots.add(buildBay("L-1", BaySize.OVERSIZED_BAY, 2, 40, 10));
        slots.add(buildBay("L-2", BaySize.OVERSIZED_BAY, 2, 45, 15));

        var manager = new GarageManager(slots);

        System.out.println("=== Multilevel Garage System ===\n");
        System.out.println("Current vacancy: " + manager.availability());

        // 1. a car enters via gate-A
        var sedan = new Automobile("KA-01-1234", AutoCategory.CAR);
        var sedanArrival = LocalDateTime.of(2025, 1, 15, 10, 0);
        var sedanTicket = manager.parkVehicle(sedan, sedanArrival, BaySize.STANDARD_BAY, "GATE-A");
        System.out.println("\nParked: " + sedanTicket);

        // 2. a two-wheeler enters via gate-B
        var scooter = new Automobile("KA-02-5678", AutoCategory.TWO_WHEELER);
        var scooterArrival = LocalDateTime.of(2025, 1, 15, 10, 30);
        var scooterTicket = manager.parkVehicle(scooter, scooterArrival, BaySize.TWO_WHEELER_BAY, "GATE-B");
        System.out.println("Parked: " + scooterTicket);

        // 3. a two-wheeler in a standard bay (smaller vehicle, bigger slot)
        var moped = new Automobile("KA-03-9999", AutoCategory.TWO_WHEELER);
        var mopedArrival = LocalDateTime.of(2025, 1, 15, 11, 0);
        var mopedTicket = manager.parkVehicle(moped, mopedArrival, BaySize.STANDARD_BAY, "GATE-A");
        System.out.println("Parked (two-wheeler in standard bay): " + mopedTicket);

        // 4. a bus enters via gate-B
        var coach = new Automobile("KA-04-0001", AutoCategory.BUS);
        var coachArrival = LocalDateTime.of(2025, 1, 15, 9, 0);
        var coachTicket = manager.parkVehicle(coach, coachArrival, BaySize.OVERSIZED_BAY, "GATE-B");
        System.out.println("Parked: " + coachTicket);

        System.out.println("\nVacancy after parking: " + manager.availability());

        // 5. the sedan leaves after 3 hours
        var sedanExit = LocalDateTime.of(2025, 1, 15, 13, 0);
        var sedanFee = manager.processExit(sedanTicket, sedanExit);
        System.out.println("\nSedan left. Fee: Rs " + sedanFee
                + " (3 hrs @ Rs " + BaySize.STANDARD_BAY.getPricePerHour() + "/hr)");

        // 6. the moped leaves after 2 hours – billed at standard-bay rate
        var mopedExit = LocalDateTime.of(2025, 1, 15, 13, 0);
        var mopedFee = manager.processExit(mopedTicket, mopedExit);
        System.out.println("Moped left (standard bay). Fee: Rs " + mopedFee
                + " (2 hrs @ Rs " + BaySize.STANDARD_BAY.getPricePerHour() + "/hr)");

        // 7. the coach leaves after 5 hours
        var coachExit = LocalDateTime.of(2025, 1, 15, 14, 0);
        var coachFee = manager.processExit(coachTicket, coachExit);
        System.out.println("Coach left. Fee: Rs " + coachFee
                + " (5 hrs @ Rs " + BaySize.OVERSIZED_BAY.getPricePerHour() + "/hr)");

        System.out.println("\nFinal vacancy: " + manager.availability());
    }

    private static Bay buildBay(String code, BaySize size, int floor,
                                int distFromA, int distFromB) {
        var gateDistances = new HashMap<String, Integer>();
        gateDistances.put("GATE-A", distFromA);
        gateDistances.put("GATE-B", distFromB);
        return new Bay(code, size, floor, gateDistances);
    }
}
