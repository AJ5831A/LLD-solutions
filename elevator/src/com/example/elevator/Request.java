package com.example.elevator;

public class Request {
    private final int requestedFloor;
    private final Direction requestedDirection;

    public Request(int requestedFloor, Direction requestedDirection) {
        this.requestedFloor = requestedFloor;
        this.requestedDirection = requestedDirection;
    }

    public int getRequestedFloor()            { return requestedFloor; }
    public Direction getRequestedDirection()  { return requestedDirection; }

    @Override
    public String toString() {
        return "Req[floor=" + requestedFloor + ", dir=" + requestedDirection + "]";
    }
}
