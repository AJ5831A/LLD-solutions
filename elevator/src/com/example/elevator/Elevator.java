package com.example.elevator;

import java.util.TreeSet;

public class Elevator {
    private final String id;
    private int currentFloor;
    private ElevatorState travelState;
    private final TreeSet<Integer> queuedStops;

    public Elevator(String id, int initialFloor) {
        this.id = id;
        this.currentFloor = initialFloor;
        this.travelState = ElevatorState.IDLE;
        this.queuedStops = new TreeSet<>();
    }

    public String getId()           { return id; }
    public int getCurrentFloor()    { return currentFloor; }
    public ElevatorState getTravelState() { return travelState; }
    public boolean isIdle()         { return travelState == ElevatorState.IDLE; }

    public void addStop(int destinationFloor) {
        queuedStops.add(destinationFloor);
        if (destinationFloor > currentFloor) {
            travelState = ElevatorState.MOVING_UP;
        } else if (destinationFloor < currentFloor) {
            travelState = ElevatorState.MOVING_DOWN;
        }
    }

    public void tick() {
        if (queuedStops.isEmpty()) {
            travelState = ElevatorState.IDLE;
            return;
        }

        // decide movement direction
        if (travelState == ElevatorState.MOVING_UP || currentFloor < queuedStops.first()) {
            currentFloor++;
        } else if (travelState == ElevatorState.MOVING_DOWN || currentFloor > queuedStops.last()) {
            currentFloor--;
        }

        // check if we reached a requested floor
        if (queuedStops.remove(currentFloor)) {
            System.out.println("  >> " + id + " stopped at floor " + currentFloor);
        }

        // recalculate state
        if (queuedStops.isEmpty()) {
            travelState = ElevatorState.IDLE;
        } else if (queuedStops.first() > currentFloor) {
            travelState = ElevatorState.MOVING_UP;
        } else {
            travelState = ElevatorState.MOVING_DOWN;
        }
    }

    @Override
    public String toString() {
        return id + " {floor=" + currentFloor + ", " + travelState
                + ", pending=" + queuedStops + "}";
    }
}
