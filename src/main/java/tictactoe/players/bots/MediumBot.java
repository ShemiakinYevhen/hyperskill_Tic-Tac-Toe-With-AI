package tictactoe.players.bots;

import java.util.Scanner;

public class MediumBot extends EasyBot {

    public MediumBot(char character) {
        super(character);
    }

    @Override
    public String getMoveCoordinates(Scanner scanner, int countOfRowsAndColumns, char[][] battleField) {
        char opponentCharacter = getPlayerCharacter() == 'X' ? 'O' : 'X';
        String coordinates;

        coordinates = checkRowsAndColumnsForAlmostWin(battleField, countOfRowsAndColumns, getPlayerCharacter());

        if (coordinates.length() > 0) {
            return coordinates;
        }

        coordinates = checkDiagonalsForAlmostWin(battleField, countOfRowsAndColumns, getPlayerCharacter());

        if (coordinates.length() > 0) {
            return coordinates;
        }

        coordinates = checkRowsAndColumnsForAlmostWin(battleField, countOfRowsAndColumns, opponentCharacter);

        if (coordinates.length() > 0) {
            return coordinates;
        }

        coordinates = checkDiagonalsForAlmostWin(battleField, countOfRowsAndColumns, opponentCharacter);

        if (coordinates.length() > 0) {
            return coordinates;
        }

        return super.getMoveCoordinates(scanner, countOfRowsAndColumns, battleField);
    }

    protected String checkRowsAndColumnsForAlmostWin(char[][] battleField, int countOfRowsAndColumns, char targetCharacter) {
        int horizontalXCount = 0;
        int horizontalOCount = 0;
        int horizontalEmptySlotsCount = 0;
        String horizontalEmptySlotCoordinate = null;
        int verticalXCount = 0;
        int verticalOCount = 0;
        int verticalEmptySlotsCount = 0;
        String verticalEmptySlotCoordinate = null;

        for (int i = 0; i < countOfRowsAndColumns; i++) {
            for (int j = 0; j < countOfRowsAndColumns; j++ ) {
                if (battleField[i][j] == 'X') {
                    horizontalXCount += 1;
                } else if (battleField[i][j] == 'O') {
                    horizontalOCount += 1;
                } else {
                    horizontalEmptySlotsCount += 1;
                    horizontalEmptySlotCoordinate = i + " " + j;
                }

                if (battleField[j][i] == 'X') {
                    verticalXCount += 1;
                } else if (battleField[j][i] == 'O') {
                    verticalOCount += 1;
                } else {
                    verticalEmptySlotsCount += 1;
                    verticalEmptySlotCoordinate = j + " " + i;
                }
            }

            if ((targetCharacter == 'X' && horizontalXCount == (countOfRowsAndColumns - 1) && horizontalEmptySlotsCount == 1) ||
                    (targetCharacter == 'O' && horizontalOCount == (countOfRowsAndColumns - 1) && horizontalEmptySlotsCount == 1)) {
                return horizontalEmptySlotCoordinate;
            } else if ((targetCharacter == 'X' && verticalXCount == (countOfRowsAndColumns - 1) && verticalEmptySlotsCount == 1) ||
                    (targetCharacter == 'O' && verticalOCount == (countOfRowsAndColumns - 1) && verticalEmptySlotsCount == 1)) {
                return verticalEmptySlotCoordinate;
            }

            horizontalXCount = 0;
            horizontalOCount = 0;
            horizontalEmptySlotsCount = 0;
            horizontalEmptySlotCoordinate = null;
            verticalXCount = 0;
            verticalOCount = 0;
            verticalEmptySlotsCount = 0;
            verticalEmptySlotCoordinate = null;
        }

        return "";
    }

    protected String checkDiagonalsForAlmostWin(char[][] battleField, int countOfRowsAndColumns, char targetCharacter) {
        int leftDiagonalXCount = 0;
        int leftDiagonalOCount = 0;
        int leftDiagonalEmptySlotsCount = 0;
        String leftDiagonalEmptySlotCoordinate = null;
        int rightDiagonalXCount = 0;
        int rightDiagonalOCount = 0;
        int rightDiagonalEmptySlotsCount = 0;
        String rightDiagonalEmptySlotCoordinate = null;

        for (int i = 0; i < countOfRowsAndColumns; i++ ) {
            if (battleField[i][i] == 'X') {
                leftDiagonalXCount += 1;
            } else if (battleField[i][i] == 'O') {
                leftDiagonalOCount += 1;
            } else {
                leftDiagonalEmptySlotsCount += 1;
                leftDiagonalEmptySlotCoordinate = i + " " + i;
            }

            if (battleField[i][countOfRowsAndColumns - i - 1] == 'X') {
                rightDiagonalXCount += 1;
            } else if (battleField[i][countOfRowsAndColumns - i - 1] == 'O') {
                rightDiagonalOCount += 1;
            } else {
                rightDiagonalEmptySlotsCount += 1;
                rightDiagonalEmptySlotCoordinate = i + " " + (countOfRowsAndColumns - i - 1);
            }
        }

        if ((targetCharacter == 'X' && leftDiagonalXCount == (countOfRowsAndColumns - 1) && leftDiagonalEmptySlotsCount == 1) ||
                (targetCharacter == 'O' && leftDiagonalOCount == (countOfRowsAndColumns - 1) && leftDiagonalEmptySlotsCount == 1)) {
            return leftDiagonalEmptySlotCoordinate;
        } else if ((targetCharacter == 'X' && rightDiagonalXCount == (countOfRowsAndColumns - 1) && rightDiagonalEmptySlotsCount == 1) ||
                (targetCharacter == 'O' && rightDiagonalOCount == (countOfRowsAndColumns - 1) && rightDiagonalEmptySlotsCount == 1)) {
            return rightDiagonalEmptySlotCoordinate;
        }

        return "";
    }

    public void printMoveOwner() {
        System.out.println("Making move level \"medium\"");
    }
}
