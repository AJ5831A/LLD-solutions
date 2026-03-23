package com.example.snakesladders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Represents the playing board with its total cell count and link map.
 * @author Aryan Jakhar
 */
public class GameBoard {
    private final int totalCells;
    private final List<Connector> links;
    private final Map<Integer, Integer> linkMap;

    public GameBoard(int totalCells, List<Connector> connectors) {
        this.totalCells = totalCells;
        this.links = new ArrayList<>(connectors);

        // pre-compute a lookup table for instant position resolution
        this.linkMap = new HashMap<>();
        for (var link : connectors) {
            linkMap.put(link.getOrigin(), link.getTarget());
        }
    }

    public int getSize() {
        return totalCells;
    }

    /** Returns the number of the winning cell. */
    public int lastSquare() {
        return totalCells;
    }

    public List<Connector> getSnakes() {
        return links.stream()
                .filter(Connector::isSnake)
                .collect(Collectors.toList());
    }

    public List<Connector> getLadders() {
        return links.stream()
                .filter(link -> !link.isSnake())
                .collect(Collectors.toList());
    }

    public List<Connector> getConnectors() {
        return links;
    }

    /** Returns the final cell after any snake/ladder redirection. */
    public int resolvePosition(int cell) {
        return linkMap.getOrDefault(cell, cell);
    }
}
