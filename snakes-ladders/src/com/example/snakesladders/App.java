package com.example.snakesladders;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Interactive entry point – collects board size, player names, and difficulty,
 * then launches the game.
 * @author Aryan Jakhar
 */
public class App {
    public static void main(String[] args) {
        var input = new Scanner(System.in);

        System.out.print("Board dimension (n for an n x n grid): ");
        var dimension = input.nextInt();

        System.out.print("Number of players: ");
        var playerCount = input.nextInt();
        input.nextLine();

        var participants = new ArrayList<Player>();
        for (var idx = 1; idx <= playerCount; idx++) {
            System.out.print("Enter name for player " + idx + ": ");
            var playerName = input.nextLine();
            participants.add(new Player(playerName));
        }

        System.out.print("Choose difficulty (easy/hard): ");
        var diffChoice = input.nextLine();
        var level = DifficultyLevel.fromInput(diffChoice);

        var board = new BoardGenerator().build(dimension, level);
        var die = new Dice();

        var game = new GameSession(board, participants, die);
        game.play();

        input.close();
    }
}
