package tictactoe.players.bots;

import tictactoe.models.Move;

import java.util.ArrayList;
import java.util.Scanner;

public class HardBot extends MediumBot {

    private char opponentCharacter;

    public HardBot(char character) {
        super(character);
        opponentCharacter = character == 'X' ? 'O' : 'X';
    }

    @Override
    public String getMoveCoordinates(Scanner scanner, int countOfRowsAndColumns, char[][] battleField) {
        return minimax(battleField, countOfRowsAndColumns, getPlayerCharacter()).getCoordinates();
    }

    private Move minimax(char[][] battleField, int countOfRowsAndColumns, char playerCharacter) {
        ArrayList<Move> moves = new ArrayList<>();
        ArrayList<String> emptyPlaces = getEmptyPlaces(battleField, countOfRowsAndColumns);

        if (checkRowsAndColumnsForWinner(battleField, countOfRowsAndColumns, opponentCharacter) ||
                checkDiagonalsForWinner(battleField, countOfRowsAndColumns, opponentCharacter)) {
            Move result = new Move();
            result.setScore(-10);
            return result;
        } else if (checkRowsAndColumnsForWinner(battleField, countOfRowsAndColumns, getPlayerCharacter()) ||
                 checkDiagonalsForWinner(battleField, countOfRowsAndColumns, getPlayerCharacter())) {
            Move result = new Move();
            result.setScore(10);
            return result;
         } else if (emptyPlaces.size() == 0) {
            Move result = new Move();
            result.setScore(0);
            return result;
        }

        for (String emptyPlace : emptyPlaces) {
            Move move = new Move();
            move.setCoordinates(emptyPlace);
            int firstCoordinate = Integer.parseInt(emptyPlace.split(" ")[0]);
            int secondCoordinate = Integer.parseInt(emptyPlace.split(" ")[1]);

            battleField[firstCoordinate][secondCoordinate] = playerCharacter;

            if (playerCharacter == getPlayerCharacter()) {
                move.setScore(minimax(battleField, countOfRowsAndColumns, opponentCharacter).getScore());
            } else {
                move.setScore(minimax(battleField, countOfRowsAndColumns, getPlayerCharacter()).getScore());
            }

            battleField[firstCoordinate][secondCoordinate] = ' ';

            moves.add(move);
        }

        int bestMove = 0;

        if (playerCharacter == getPlayerCharacter()) {
            int bestScore = -10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() > bestScore) {
                    bestScore = moves.get(i).getScore();
                    bestMove = i;
                }
            }
        } else {
            int bestScore = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() < bestScore) {
                    bestScore = moves.get(i).getScore();
                    bestMove = i;
                }
            }
        }

        return moves.get(bestMove);
    }

    private ArrayList<String> getEmptyPlaces(char[][] battleField, int countOfRowsAndColumns) {
        ArrayList<String> emptyPlaces = new ArrayList<>();
        for (int i = 0; i < countOfRowsAndColumns; i++) {
            for (int j = 0; j < countOfRowsAndColumns; j++) {
                if (battleField[i][j] == ' ') {
                    emptyPlaces.add(i + " " + j);
                }
            }
        }

        return emptyPlaces;
    }

    private boolean checkRowsAndColumnsForWinner(char[][] battleField, int countOfRowsAndColumns, char playerCharacter) {
        boolean horizontalWin = true;
        boolean verticalWin = true;

        for (int i = 0; i < countOfRowsAndColumns; i++) {
            for (int j = 0; j < countOfRowsAndColumns - 1; j++) {
                if (battleField[i][j] != battleField[i][j + 1]) {
                    horizontalWin = false;
                }

                if (battleField[j][i] != battleField[j + 1][i]) {
                    verticalWin = false;
                }
            }

            if ((horizontalWin || verticalWin) && battleField[i][i] == playerCharacter) {
                return true;
            }

            horizontalWin = true;
            verticalWin = true;
        }

        return false;
    }

    private boolean checkDiagonalsForWinner(char[][] battleField, int countOfRowsAndColumns, char playerCharacter) {
        boolean leftDiagonalWin = true;
        boolean rightDiagonalWin = true;

        for (int i = 0; i < countOfRowsAndColumns - 1; i++) {
            if (battleField[i][i] != battleField[i + 1][i + 1]) {
                leftDiagonalWin = false;
            }

            if (battleField[i][countOfRowsAndColumns - i - 1] != battleField[i + 1][countOfRowsAndColumns - i - 2]) {
                rightDiagonalWin = false;
            }
        }

        return (leftDiagonalWin && battleField[0][0] == playerCharacter) ||
                (rightDiagonalWin && battleField[0][countOfRowsAndColumns - 1] == playerCharacter);
    }

    public void printMoveOwner() {
        System.out.println("Making move level \"hard\"");
    }
}
