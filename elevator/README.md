# Elevator System

## Class Diagram

```
+---------------------+       +---------------------+
|      <<enum>>       |       |      <<enum>>       |
|      Direction      |       |   ElevatorState     |
|---------------------|       |---------------------|
| UP                  |       | IDLE                |
| DOWN                |       | MOVING_UP           |
|---------------------|       | MOVING_DOWN         |
| +isUp(): boolean    |       |---------------------|
+---------------------+       | +isMoving(): boolean|
                               +---------------------+

+---------------------+
|       Request       |
|---------------------|
| -requestedFloor: int|
| -requestedDirection |
+---------------------+
          |
          | dispatched to
          v
+----------------------------------------------+
|                  Elevator                    |
|----------------------------------------------|
| -id: String                                  |
| -currentFloor: int                           |
| -travelState: ElevatorState                  |
| -queuedStops: TreeSet<Integer>               |
|----------------------------------------------|
| +addStop(destinationFloor): void             |
| +tick(): void                                |
| +isIdle(): boolean                           |
+----------------------------------------------+
          ^
          | manages
+----------------------------------------------+
|             ElevatorController               |
|----------------------------------------------|
| -elevators: List<Elevator>                   |
| -OPPOSITE_DIRECTION_PENALTY: int             |
|----------------------------------------------|
| +assignRequest(request): void                |
| +computeScore(elevator, request): int        |
| +tick(): void                                |
| +printStatus(): void                         |
+----------------------------------------------+
          ^
          | uses
+----------------------------------------------+
|                  Building                    |
|----------------------------------------------|
| -totalFloors: int                            |
| -controller: ElevatorController              |
|----------------------------------------------|
| +requestElevator(floor, dir): void           |
| +tick(): void                                |
| +printStatus(): void                         |
+----------------------------------------------+
```

## Design Approach

### Movement Logic
Each `Elevator` maintains a `TreeSet<Integer>` of queued stops. Since it’s ordered, the elevator can quickly reason about the next extremes. On each `tick()`, the cabin moves exactly one floor toward its next stop, removes the stop when it arrives, and refreshes its motion state. If no stops remain, it becomes IDLE.

### Cost-Based Dispatch
`ElevatorController.assignRequest()` selects the best elevator for a request via `computeScore()`:
- **Idle elevator**: cost equals the straight-line distance.
- **Same-direction, heading toward caller**: cost equals the distance (the request is on its route).
- **Opposite direction / moving away**: cost = `OPPOSITE_DIRECTION_PENALTY` (120) + distance — so these elevators are picked only as a last resort.

The elevator with the minimum cost is assigned the request.

### Building Facade
`Building` serves as the public entry point. It validates the floor input, wraps it into a `Request`, and forwards it to the controller. Callers never interact with elevators directly.

### Step-Driven Simulation
The simulation advances by calling `building.tick()`, which moves every elevator by one step per tick. This makes it easy to trace and test behaviour at each discrete point in time.

## How to Build and Run

```bash
cd elevator
javac -d out src/com/example/elevator/*.java
java -cp out com.example.elevator.App
```
