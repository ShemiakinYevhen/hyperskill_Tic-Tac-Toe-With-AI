package tictactoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String enteredCommand;
        String[] optionsToStartGame;

        while(true) {
            System.out.print("Input command: ");
            enteredCommand = scanner.nextLine();

            if (enteredCommand.equals("exit")) {
                return;
            }

            try {
                optionsToStartGame = enteredCommand.split(" ");
            } catch (Exception e) {
                System.out.println("Bad parameters!");
                continue;
            }

            if (optionsToStartGame.length != 3 ||
                    !optionsToStartGame[0].equals("start") ||
                    (!optionsToStartGame[1].equals("user") &&
                            !optionsToStartGame[1].equals("easy") &&
                            !optionsToStartGame[1].equals("medium") &&
                            !optionsToStartGame[1].equals("hard")) ||
                    (!optionsToStartGame[2].equals("user") &&
                            !optionsToStartGame[2].equals("easy") &&
                            !optionsToStartGame[2].equals("medium") &&
                            !optionsToStartGame[2].equals("hard"))){
                System.out.println("Bad parameters!");
                continue;
            }

            GameManager gameManager = new GameManager(optionsToStartGame[1], optionsToStartGame[2]);

            gameManager.start(scanner);
        }
    }
}
