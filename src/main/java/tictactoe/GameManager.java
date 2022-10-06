package tictactoe;

import tictactoe.players.Player;
import tictactoe.players.User;
import tictactoe.players.bots.EasyBot;
import tictactoe.players.bots.HardBot;
import tictactoe.players.bots.MediumBot;

import java.util.Arrays;
import java.util.Scanner;

public class GameManager {

    private static final int COUNT_OF_ROWS_AND_COLUMNS = 3;
    private static final String ERROR_MESSAGE_FOR_INVALID_INITIAL_STATE_INPUT = "Please, enter 9 symbols representing " +
            "'X', 'O' or '_', leave input field empty to start a brand new game or enter 'exit' to end the game";
    private static boolean emptyPlaceIndicator = false;
    private static boolean xWins = false;
    private static boolean oWins = false;
    private static char[][] battleField = new char[COUNT_OF_ROWS_AND_COLUMNS][COUNT_OF_ROWS_AND_COLUMNS];
    private Player xPlayer;
    private Player oPlayer;

    public GameManager(String xPlayerType, String oPlayerType) {
        xPlayer = setPlayer(xPlayerType, 'X');
        oPlayer = setPlayer(oPlayerType, 'O');
    }

    public Player getXPlayer() {
        return xPlayer;
    }

    public void setXPlayer(Player xPlayer) {
        this.xPlayer = xPlayer;
    }

    public Player getOPlayer() {
        return oPlayer;
    }

    public void setOPlayer(Player oPlayer) {
        this.oPlayer = oPlayer;
    }

    public Player setPlayer(String playerType, char playerCharacter) {
        Player player = null;
        switch(playerType) {
            case "user":
                player = new User(playerCharacter);
                break;
            case "easy":
                player = new EasyBot(playerCharacter);
                break;
            case "medium":
                player = new MediumBot(playerCharacter);
                break;
            case "hard":
                player = new HardBot(playerCharacter);
                break;
        }
        return player;
    }

    public void start(Scanner scanner) {
        String result;

        try {
            getInitialStateOfTheGameAutomatically();
            result = playTheGame(scanner);
        } catch (Exception e) {
            return;
        }

        printFormattedGameState();
        System.out.println(result);
        xWins = false;
        oWins = false;
    }

    private void getInitialStateOfTheGameAutomatically() {
        char[] oneFilledRow = new char[COUNT_OF_ROWS_AND_COLUMNS];
        Arrays.fill(oneFilledRow, ' ');
        for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
            System.arraycopy(oneFilledRow, 0, battleField[i], 0, COUNT_OF_ROWS_AND_COLUMNS);
        }
        printFormattedGameState();
    }

    private void getInitialStateOfTheGameFromUser(Scanner scanner) throws Exception {
        System.out.print("Enter the cells: ");
        String initialBattleFieldInput = "";

        while (true) {
            initialBattleFieldInput = scanner.nextLine();

            if (initialBattleFieldInput.length() == 0) {
                for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
                    System.arraycopy("         ".toCharArray(), i * COUNT_OF_ROWS_AND_COLUMNS, battleField[i], 0, COUNT_OF_ROWS_AND_COLUMNS);
                }
                printFormattedGameState();
                return;
            } else if (initialBattleFieldInput.length() != COUNT_OF_ROWS_AND_COLUMNS * COUNT_OF_ROWS_AND_COLUMNS) {
                System.out.println(ERROR_MESSAGE_FOR_INVALID_INITIAL_STATE_INPUT);
                System.out.print("Enter the cells: ");
            } else {
                for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
                    for (int j = 0; j < COUNT_OF_ROWS_AND_COLUMNS; j++) {
                        battleField[i][j] = initialBattleFieldInput.charAt(i * 3 + j);
                    }
                }

                String currentGameState;

                try {
                    currentGameState = calculateCurrentGameState();
                } catch (Exception e) {
                    System.out.println(ERROR_MESSAGE_FOR_INVALID_INITIAL_STATE_INPUT);
                    System.out.print("Enter the cells: ");
                    continue;
                }

                switch (currentGameState) {
                    case "Draw":
                    case "X wins":
                    case "O wins":
                        printFormattedGameState();
                        System.out.println(currentGameState);
                        System.out.println("Previous game was finished. But don't worry, let's start a new one!");
                        System.out.print("Enter the cells: ");
                        continue;
                    case "Impossible":
                        printFormattedGameState();
                        System.out.println(currentGameState);
                        System.out.print("Enter the cells: ");
                        continue;
                    case "Game not finished":
                        printFormattedGameState();
                        return;
                }
            }
        }
    }

    private String calculateCurrentGameState() throws Exception {
        int differenceOfCountOfXsAndOs = checkForInvalidCharacterAndGetCountOfXsAndOsDifference();

        checkRowsAndColumnsForWinner();
        checkDiagonalsForWinner();

        if (differenceOfCountOfXsAndOs > 1 || (xWins && oWins)) {
            return "Impossible";
        }

        if (!xWins && !oWins) {
            if (emptyPlaceIndicator) {
                emptyPlaceIndicator = false;
                return "Game not finished";
            } else {
                return "Draw";
            }
        } else if (xWins) {
            return "X wins";
        } else {
            return "O wins";
        }
    }

    private int checkForInvalidCharacterAndGetCountOfXsAndOsDifference() throws Exception {
        int xCounter = 0;
        int oCounter = 0;

        for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
            for (int j = 0; j < COUNT_OF_ROWS_AND_COLUMNS; j++) {
                if (battleField[i][j] == '_' || battleField[i][j] == ' ') {
                    emptyPlaceIndicator = true;
                    battleField[i][j] = ' ';
                } else if (battleField[i][j] == 'X') {
                    xCounter += 1;
                } else if (battleField[i][j] == 'O') {
                    oCounter += 1;
                } else {
                    throw new Exception("Unrecognized symbol found!");
                }
            }
        }

        return Math.abs(xCounter - oCounter);
    }

    private void checkRowsAndColumnsForWinner() {
        boolean horizontalWin = true;
        boolean verticalWin = true;

        for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
            for (int j = 0; j < COUNT_OF_ROWS_AND_COLUMNS - 1; j++) {
                if (battleField[i][j] != battleField[i][j + 1]) {
                    horizontalWin = false;
                }

                if (battleField[j][i] != battleField[j + 1][i]) {
                    verticalWin = false;
                }
            }

            if (horizontalWin || verticalWin) {
                if (battleField[i][i] == 'X') {
                    xWins = true;
                } else if (battleField[i][i] == 'O') {
                    oWins = true;
                }
            }

            horizontalWin = true;
            verticalWin = true;
        }
    }

    private void checkDiagonalsForWinner() {
        boolean leftDiagonalWin = true;
        boolean rightDiagonalWin = true;

        for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS - 1; i++) {
            if (battleField[i][i] != battleField[i + 1][i + 1]) {
                leftDiagonalWin = false;
            }

            if (battleField[i][COUNT_OF_ROWS_AND_COLUMNS - i - 1] != battleField[i + 1][COUNT_OF_ROWS_AND_COLUMNS - i - 2]) {
                rightDiagonalWin = false;
            }
        }

        if (leftDiagonalWin) {
            if (battleField[0][0] == 'X') {
                xWins = true;
            } else if (battleField[0][0] == 'O') {
                oWins = true;
            }
        }

        if (rightDiagonalWin) {
            if (battleField[0][COUNT_OF_ROWS_AND_COLUMNS - 1] == 'X') {
                xWins = true;
            } else if (battleField[0][COUNT_OF_ROWS_AND_COLUMNS - 1] == 'O') {
                oWins = true;
            }
        }
    }

    public String playTheGame(Scanner scanner) throws Exception {
        String result;
        String moveCoordinates;

        while (true) {
            moveCoordinates = xPlayer.getMoveCoordinates(scanner, COUNT_OF_ROWS_AND_COLUMNS, battleField);
            xPlayer.printMoveOwner();
            battleField[Integer.parseInt(moveCoordinates.split(" ")[0])][Integer.parseInt(moveCoordinates.split(" ")[1])] = xPlayer.getPlayerCharacter();
            result = calculateCurrentGameState();
            if (!result.equals("Game not finished")) {
                return result;
            } else {
                printFormattedGameState();
            }

            moveCoordinates = oPlayer.getMoveCoordinates(scanner, COUNT_OF_ROWS_AND_COLUMNS, battleField);
            oPlayer.printMoveOwner();
            battleField[Integer.parseInt(moveCoordinates.split(" ")[0])][Integer.parseInt(moveCoordinates.split(" ")[1])] = oPlayer.getPlayerCharacter();
            result = calculateCurrentGameState();
            if (!result.equals("Game not finished")) {
                return result;
            } else {
                printFormattedGameState();
            }
        }
    }

    private void printFormattedGameState() {
        System.out.print(" -");
        for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
            System.out.print("--");
        }
        System.out.println();
        for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
            System.out.print("| ");
            for (int j = 0; j < COUNT_OF_ROWS_AND_COLUMNS; j++) {
                System.out.print(battleField[i][j] + " ");
            }
            System.out.print("|\n");
        }
        System.out.print(" -");
        for (int i = 0; i < COUNT_OF_ROWS_AND_COLUMNS; i++) {
            System.out.print("--");
        }
        System.out.println();
    }
}
