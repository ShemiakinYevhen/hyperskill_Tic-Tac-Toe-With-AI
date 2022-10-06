package tictactoe.players;

import java.util.Scanner;

public class User extends Player {

    public User(char character) {
        super(character);
    }

    @Override
    public String getMoveCoordinates(Scanner scanner, int countOfRowsAndColumns, char[][] battleField) {
        String coordinatesUserInput;
        int firstCoordinate, secondCoordinate;

        while (true) {
            System.out.print("Enter the coordinates: ");
            coordinatesUserInput = scanner.nextLine();

            try {
                firstCoordinate = Integer.parseInt(coordinatesUserInput.split(" ")[0]);
                secondCoordinate = Integer.parseInt(coordinatesUserInput.split(" ")[1]);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You should enter two numbers!");
                continue;
            }

            if (firstCoordinate < 1 || firstCoordinate > countOfRowsAndColumns || secondCoordinate < 1 || secondCoordinate > countOfRowsAndColumns) {
                System.out.println(String.format("Coordinates should be from 1 to %d!", countOfRowsAndColumns));
                continue;
            }

            firstCoordinate -= 1;
            secondCoordinate -= 1;

            if (battleField[firstCoordinate][secondCoordinate] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            return firstCoordinate + " " + secondCoordinate;
        }
    }

    @Override
    public void printMoveOwner() {}
}
