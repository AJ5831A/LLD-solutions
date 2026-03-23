package com.example.snakesladders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Procedurally generates a balanced board with snakes and ladders
 * based on the chosen difficulty level.
 * @author Aryan Jakhar
 */
public class BoardGenerator {
    private final Random rng;

    public BoardGenerator() {
        this.rng = new Random();
    }

    public GameBoard build(int n, DifficultyLevel difficulty) {
        var cellCount = n * n;

        // scale the difficulty percentages to absolute cell counts
        var minDrop  = Math.max(1, cellCount * difficulty.getSnakeMinDrop() / 100);
        var maxDrop  = Math.max(minDrop + 1, cellCount * difficulty.getSnakeMaxDrop() / 100);
        var minClimb = Math.max(1, cellCount * difficulty.getLadderMinClimb() / 100);
        var maxClimb = Math.max(minClimb + 1, cellCount * difficulty.getLadderMaxClimb() / 100);

        // cells already used by a connector endpoint
        Set<Integer> usedCells = new HashSet<>();
        usedCells.add(1);
        usedCells.add(cellCount);

        // origin → destination for cycle prevention
        Map<Integer, Integer> jumpTable = new HashMap<>();

        List<Connector> result = new ArrayList<>();

        // create n snakes
        for (var i = 0; i < n; i++) {
            var snake = tryCreateSnake(cellCount, minDrop, maxDrop, usedCells, jumpTable);
            if (snake != null) result.add(snake);
        }

        // create n ladders
        for (var i = 0; i < n; i++) {
            var ladder = tryCreateLadder(cellCount, minClimb, maxClimb, usedCells, jumpTable);
            if (ladder != null) result.add(ladder);
        }

        return new GameBoard(cellCount, result);
    }

    private Connector tryCreateSnake(int cellCount, int minDrop, int maxDrop,
                                     Set<Integer> usedCells, Map<Integer, Integer> jumpTable) {
        for (var trial = 0; trial < 100; trial++) {
            var drop = minDrop + rng.nextInt(maxDrop - minDrop + 1);
            var head = drop + 1 + rng.nextInt(cellCount - drop - 1);
            var tail = head - drop;

            if (tail < 1 || head >= cellCount) continue;
            if (usedCells.contains(head) || usedCells.contains(tail)) continue;
            if (jumpTable.containsKey(tail)) continue;  // avoid cycles

            usedCells.add(head);
            usedCells.add(tail);
            jumpTable.put(head, tail);
            return new Connector(head, tail);
        }
        return null;
    }

    private Connector tryCreateLadder(int cellCount, int minClimb, int maxClimb,
                                      Set<Integer> usedCells, Map<Integer, Integer> jumpTable) {
        for (var trial = 0; trial < 100; trial++) {
            var rise = minClimb + rng.nextInt(maxClimb - minClimb + 1);
            var bottom = 2 + rng.nextInt(cellCount - rise - 2);
            var top = bottom + rise;

            if (top >= cellCount || bottom < 2) continue;
            if (usedCells.contains(bottom) || usedCells.contains(top)) continue;
            if (jumpTable.containsKey(top)) continue;  // avoid cycles

            usedCells.add(bottom);
            usedCells.add(top);
            jumpTable.put(bottom, top);
            return new Connector(bottom, top);
        }
        return null;
    }
}
