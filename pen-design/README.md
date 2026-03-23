# Pen Design

## Class Diagram

```
+------------------+
|   <<enum>>       |
|   InkColor       |
|------------------|
| BLACK            |
| BLUE             |
| RED              |
| GREEN            |
+------------------+

+------------------+
|     Refill       |
|------------------|
| -color: InkColor |
| -inkLevel: int   |
| -maxInk: int     |
|------------------|
| +consumeInk(int) |
| +refillInk()     |
| +isEmpty(): bool |
| +getInkLevel()   |
+------------------+
         |
         | used by
         v
+--------------------------------------+
|         <<interface>> Pen            |
|--------------------------------------|
| +start(): void                       |
| +write(text: String): void           |
| +close(): void                       |
| +refill(newRefill: Refill): void     |
| +isOpen(): boolean                   |
+--------------------------------------+
     ^            ^            ^
     |            |            |
+------------+ +------------+ +------------+
|BallPointPen| |FountainPen | | MarkerPen  |
|------------| |------------| |------------|
| -refill    | | -refill    | | -refill    |
| -open: bool| | -open: bool| | -open: bool|
|------------| |------------| |------------|
| click open | | uncap      | | uncap      |
| 1 ink/char | | 2 ink/char | | 3 ink/char |
| replace    | | replenish  | | throws     |
| cartridge  | | reservoir  | | exception  |
+------------+ +------------+ +------------+
```

## Design Approach

### Interface-Driven Polymorphism
All pen types implement the `Pen` interface with the same four operations: `start()`, `write()`, `close()`, and `refill()`. Each type provides its own behavior for these methods.

### Pen Types and Their Differences

| Feature | BallPointPen | FountainPen | MarkerPen |
|---------|-------------|-------------|-----------|
| Open/Close | Click mechanism | Cap on/off | Cap on/off |
| Ink per char | 1 unit | 2 units | 3 units |
| Refill | Replace cartridge | Replenish reservoir | Not supported |

### Ink Management (Refill)
The `Refill` class tracks ink color, current level, and max capacity. Each pen type consumes ink at a different rate per character written. When ink runs out mid-write, the pen writes as many characters as the ink allows and warns the user.

### Refill Behavior
This is where each pen type differs the most:
- **BallPointPen**: swaps the entire refill cartridge with a new one
- **FountainPen**: refills the existing ink reservoir back to max (like using an ink bottle)
- **MarkerPen**: throws `UnsupportedOperationException` because markers are disposable

## How to Build and Run

```bash
cd pen-design
javac -d out src/com/example/pen/*.java
java -cp out com.example.pen.App
```
