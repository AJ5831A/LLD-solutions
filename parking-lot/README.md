# Multilevel Parking Lot

## Class Diagram

```
+-------------------+        +-------------------+
|    <<enum>>       |        |    <<enum>>       |
|    SlotType       |        |   VehicleType     |
|-------------------|        |-------------------|
| SMALL  (rate=10)  |        | TWO_WHEELER       |
| MEDIUM (rate=20)  |        | CAR               |
| LARGE  (rate=50)  |        | BUS               |
|-------------------|        |-------------------|
| +getHourlyRate()  |        | +fitsIn(SlotType) |
+-------------------+        +-------------------+
         ^                           ^
         |                           |
+-------------------+        +-------------------+
|    ParkingSlot    |        |     Vehicle       |
|-------------------|        |-------------------|
| -slotId: String   |        | -licensePlate     |
| -type: SlotType   |        | -type: VehicleType|
| -floor: int       |        +-------------------+
| -distanceFromGate |                |
| -occupied: boolean|                |
|-------------------|                |
| +getDistanceFrom()|                |
| +occupy()         |                |
| +release()        |                |
+-------------------+                |
         |                           |
         v                           v
+------------------------------------------------+
|                ParkingTicket                    |
|------------------------------------------------|
| -ticketId: String                              |
| -vehicle: Vehicle                              |
| -slot: ParkingSlot                             |
| -entryTime: LocalDateTime                      |
+------------------------------------------------+
         |
         v
+------------------------------------------------+
|                 ParkingLot                      |
|------------------------------------------------|
| -slots: List<ParkingSlot>                      |
| -ticketCounter: int                            |
|------------------------------------------------|
| +park(vehicle, entryTime, slotType, gateId)    |
|       -> ParkingTicket                         |
| +exit(ticket, exitTime) -> int                 |
| +status() -> Map<SlotType, Integer>            |
+------------------------------------------------+

+-------------------+
|    EntryGate      |
|-------------------|
| -gateId: String   |
+-------------------+
```

## Design Approach

### Slot Assignment
Each `ParkingSlot` stores a map of distances from every entry gate. When `park()` is called, it scans all slots that match the requested type, are not occupied, and are compatible with the vehicle type. Among those, it picks the one with the smallest distance from the given entry gate.

### Vehicle-Slot Compatibility
A `fitsIn()` method on `VehicleType` handles the compatibility rules:
- TWO_WHEELER can park in SMALL, MEDIUM, or LARGE
- CAR can park in MEDIUM or LARGE
- BUS can only park in LARGE

### Billing
Billing is based on the **slot type**, not the vehicle type. So if a bike parks in a medium slot, it pays the medium rate (Rs 20/hr). The `exit()` method calculates the parking duration in hours and multiplies by the slot's hourly rate.

### Multiple Entry Gates
Gates are identified by string IDs. Each slot knows its distance from each gate. This allows the system to assign the nearest available slot relative to whichever gate the vehicle enters from.

## How to Build and Run

```bash
cd parking-lot
javac -d out src/com/example/parking/*.java
java -cp out com.example.parking.App
```
