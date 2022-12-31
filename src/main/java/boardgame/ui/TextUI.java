package boardgame.ui;

import boardgame.InvalidBoardPickException;
import boardgame.TicTacToeGame;

import java.util.Scanner;

public class TextUI {
    private TicTacToeGame tttGame;
    private int userInput;
    private int acrossInput;
    private int downInput;
    private Scanner userScanner;

    public static void main(String[] args) {
        TextUI myTextUI;

        myTextUI = new TextUI();
        myTextUI.runGame();
    }

    public TextUI() {
        tttGame = new TicTacToeGame();
        setUserScanner(new Scanner(System.in));
    }

    public void runGame() {
        boolean validTurn;

        printIntro();

        System.out.println(getTttGame().getGameStateMessage());

        // loop to run game
        while (!getTttGame().isDone()) {
            printTurn();
            // keeps iterating until a non-set value is picked
            do {
                getInputs();
                validTurn = getTttGame().takeTurn(getAcrossInput(), getDownInput(), getTttGame().getCurrentPlayer());

                if (!validTurn) {
                    System.out.println("Your selected square has already been picked!");
                }
            } while (!validTurn);

            System.out.println(getTttGame().getGameStateMessage() + "\n\n");
        }

        getUserScanner().close();

        // Printing out winner (if there is one)
        printOutro();
    }

    public void printIntro() {
        System.out.println("Welcome to Isaiah's Text-Based Tic-Toe-Game!\n");
        System.out.println("This game is for 2 players only, so please find a partner to play with.");
        System.out.println("Please input a number on the board to make the first move.\n");
    }

    public void printOutro() {
        System.out.println("Game over!");
        
        if (getTttGame().getWinner() != -1) {
            System.out.println("The winner is Player " + getTttGame().getCurrentPlayer() + "!");
        } else {
            System.out.println("All of the squares are filled up! There is no winner!");
        }

        System.out.println("Please play again.");
    }

    public void printTurn() {
        System.out.println("Turn #" + getTttGame().getTurn());
        System.out.println("Player " + getTttGame().getCurrentPlayer() + ", it is your turn.");
    }

    //gets and sets up all of the inputs for a turn
    public void getInputs() {
        setUserInput();
        setAcrossInput(userInput);
        setDownInput(userInput);
    }

    public void setAcrossInput(int theUserInput) {
        acrossInput = theUserInput / getTttGame().getDimension() + 1;
    }

    public int getAcrossInput() {
        return acrossInput;
    }

    public void setDownInput(int theUserInput) {
        downInput = theUserInput % getTttGame().getDimension() + 1;
    }

    public int getDownInput() {
        return downInput;
    }

    public void setUserInput() {
        boolean validInput;
        validInput = false;
        System.out.println("Please enter a value from [1-9] that is not taken.");

        do {
            try {
                System.out.println("Enter your choice:");    
                
                userInput = Integer.parseInt(getUserScanner().nextLine());

                System.out.println();

                if (userInput > 9 || userInput < 1) {
                    throw new InvalidBoardPickException();
                }

                userInput -= 1; // subtracting by 1 to make the first index start at 0

                validInput = true;
            } catch (Exception e) {
                System.out.println("You didn't enter a valid input.");
            }
        } while (!validInput);
    }

    public int getUserInput() {
        return userInput;
    }

    public void setTttGame(TicTacToeGame thetttGame) {
        tttGame = thetttGame;
    }

    public TicTacToeGame getTttGame() {
        return tttGame;
    }

    public void setUserScanner(Scanner theUserScanner) {
        userScanner = theUserScanner;
    }

    public Scanner getUserScanner() {
        return userScanner;
    }
}
