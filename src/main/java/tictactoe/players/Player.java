package tictactoe.players;

import java.util.Scanner;

public abstract class Player {

    private char playerCharacter;

    public Player(char playerCharacter) {
        setPlayerCharacter(playerCharacter);
    }

    public abstract String getMoveCoordinates(Scanner scanner, int countOfRowsAndColumns, char[][] battleField);

    public abstract void printMoveOwner();

    public char getPlayerCharacter() {
        return playerCharacter;
    }

    public void setPlayerCharacter(char character) {
        playerCharacter = character;
    }
}
