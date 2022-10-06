package tictactoe.players.bots;

import tictactoe.players.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EasyBot extends Player {

    public EasyBot(char character) {
        super(character);
    }

    @Override
    public String getMoveCoordinates(Scanner scanner, int countOfRowsAndColumns, char[][] battleField) {
        ArrayList<String> emptyPlaces = new ArrayList<>();
        for (int i = 0; i < countOfRowsAndColumns; i++) {
            for (int j = 0; j < countOfRowsAndColumns; j++) {
                if (battleField[i][j] == ' ') {
                    emptyPlaces.add(i + " " + j);
                }
            }
        }
        Random random = new Random();
        return emptyPlaces.get(random.nextInt(emptyPlaces.size()));
    }

    public void printMoveOwner() {
        System.out.println("Making move level \"easy\"");
    }
}
