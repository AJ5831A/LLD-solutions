package com.example.elevator;

public class Building {
    private final int totalFloors;
    private final ElevatorController controller;

    public Building(int totalFloors, ElevatorController controller) {
        this.totalFloors = totalFloors;
        this.controller = controller;
    }

    public int getTotalFloors() { return totalFloors; }

    public void requestElevator(int requestedFloor, Direction requestedDirection) {
        if (requestedFloor < 1 || requestedFloor > totalFloors) {
            throw new IllegalArgumentException("Invalid floor: " + requestedFloor);
        }
        controller.assignRequest(new Request(requestedFloor, requestedDirection));
    }

    public void tick() {
        controller.tick();
    }

    public void printStatus() {
        controller.printStatus();
    }
}
