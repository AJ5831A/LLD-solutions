package com.example.elevator;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // configure a 12-floor building with 3 elevators
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(new Elevator("L1", 1));
        elevators.add(new Elevator("L2", 6));
        elevators.add(new Elevator("L3", 12));

        ElevatorController controller = new ElevatorController(elevators);
        Building building = new Building(12, controller);

        System.out.println("=== Elevator Simulation ===\n");
        System.out.println("Starting positions:");
        building.printStatus();

        // send a few requests
        System.out.println("\n-- Dispatching initial requests --");
        building.requestElevator(4, Direction.UP);
        building.requestElevator(8, Direction.DOWN);
        building.requestElevator(3, Direction.UP);

        System.out.println("\nPost-dispatch:");
        building.printStatus();

        // run 8 ticks of the simulation
        System.out.println("\n-- Simulation (phase 1) --");
        for (int t = 1; t <= 8; t++) {
            System.out.println("Tick " + t + ":");
            building.tick();
            building.printStatus();
        }

        // submit more requests mid-run
        System.out.println("\n-- Additional requests --");
        building.requestElevator(11, Direction.UP);
        building.requestElevator(2, Direction.UP);

        System.out.println("\nPost-dispatch:");
        building.printStatus();

        System.out.println("\n-- Simulation (phase 2) --");
        for (int t = 1; t <= 6; t++) {
            System.out.println("Tick " + t + ":");
            building.tick();
            building.printStatus();
        }
    }
}
