package com.example.elevator;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {
    private static final int OPPOSITE_DIRECTION_PENALTY = 120;
    private final List<Elevator> elevators;

    public ElevatorController(List<Elevator> elevators) {
        this.elevators = new ArrayList<>(elevators);
    }

    public void assignRequest(Request request) {
        Elevator chosen = null;
        int bestScore = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int score = computeScore(elevator, request);
            if (score < bestScore) {
                bestScore = score;
                chosen = elevator;
            }
        }

        if (chosen != null) {
            chosen.addStop(request.getRequestedFloor());
            System.out.println("Assigned " + request + " => " + chosen.getId());
        }
    }

    private int computeScore(Elevator elevator, Request request) {
        int distance = Math.abs(elevator.getCurrentFloor() - request.getRequestedFloor());

        if (elevator.isIdle()) {
            return distance;
        }

        boolean goingUp = elevator.getTravelState() == ElevatorState.MOVING_UP;
        boolean requestAboveOrAt = request.getRequestedFloor() >= elevator.getCurrentFloor();

        // elevator heading toward the request in the matching direction
        if (goingUp && request.getRequestedDirection().isUp() && requestAboveOrAt) {
            return distance;
        }
        if (!goingUp && !request.getRequestedDirection().isUp() && !requestAboveOrAt) {
            return distance;
        }

        return OPPOSITE_DIRECTION_PENALTY + distance;
    }

    public void tick() {
        for (Elevator elevator : elevators) {
            elevator.tick();
        }
    }

    public void printStatus() {
        for (Elevator elevator : elevators) {
            System.out.println("  " + elevator);
        }
    }
}
