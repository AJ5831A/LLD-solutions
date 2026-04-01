package com.example.elevator;

public enum ElevatorState {
    IDLE,
    MOVING_UP,
    MOVING_DOWN;

    public boolean isMoving() { return this != IDLE; }
}
