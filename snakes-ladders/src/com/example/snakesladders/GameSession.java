package com.example.snakesladders;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

/**
 * Drives the game loop: manages turn order, dice rolls, position updates,
 * and final rankings.
 * @author Aryan Jakhar
 */
public class GameSession {
    private final GameBoard board;
    private final List<Player> roster;
    private final Queue<Player> turnOrder;
    private final Dice dice;
    private int rankCounter;

    public GameSession(GameBoard board, List<Player> players, Dice dice) {
        this.board = board;
        this.roster = new ArrayList<>(players);
        this.turnOrder = new LinkedList<>(players);
        this.dice = dice;
        this.rankCounter = 0;
    }

    /** Runs the game until all players finish or only one remains. */
    public void play() {
        printBoardInfo();

        while (activePlayers() >= 2) {
            var player = turnOrder.poll();
            if (player == null) break;

            if (player.isFinished()) continue;

            handleTurn(player);

            // add back to the queue if still playing
            if (!player.isFinished()) {
                turnOrder.add(player);
            }
        }

        // assign rank to the last remaining player
        for (var p : roster) {
            if (!p.isFinished()) {
                rankCounter++;
                p.setRank(rankCounter);
                p.setFinished(true);
            }
        }

        printFinalRankings();
    }

    private void handleTurn(Player player) {
        var diceValue = dice.roll();
        var before = player.getPosition();
        var after = before + diceValue;

        System.out.print(player.getName() + " rolled " + diceValue + " | cell " + before);

        // cannot overshoot the last cell
        if (after > board.lastSquare()) {
            System.out.println(" -> blocked (overshoots cell " + board.lastSquare() + ")");
            return;
        }

        // check for a snake or ladder at the landing cell
        var finalCell = board.resolvePosition(after);
        if (finalCell != after) {
            var event = finalCell < after ? "Bitten by snake!" : "Climbed a ladder!";
            System.out.println(" -> " + after + " " + event + " -> " + finalCell);
            after = finalCell;
        } else {
            System.out.println(" -> " + after);
        }

        player.moveTo(after);

        if (after == board.lastSquare()) {
            rankCounter++;
            player.setRank(rankCounter);
            player.setFinished(true);
            System.out.println("  " + player.getName() + " reached the end! Rank: #" + rankCounter);
        }
    }

    private int activePlayers() {
        return (int) roster.stream()
                .filter(p -> !p.isFinished())
                .count();
    }

    private void printBoardInfo() {
        System.out.println("\n--- Snakes and Ladders ---");
        System.out.println("Total cells: " + board.getSize());
        System.out.println("Snakes:  " + board.getSnakes());
        System.out.println("Ladders: " + board.getLadders());
        System.out.println("Players: " + roster);
        System.out.println();
    }

    private void printFinalRankings() {
        System.out.println("\n--- Final Rankings ---");
        for (var pos = 1; pos <= roster.size(); pos++) {
            for (var p : roster) {
                if (p.getRank() == pos) {
                    System.out.println("#" + pos + " - " + p.getName());
                }
            }
        }
    }
}
