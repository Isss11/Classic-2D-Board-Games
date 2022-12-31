package boardgame;

public class InvalidBoardPickException extends Exception {
    public InvalidBoardPickException() {
        System.out.println("You can't pick a number that is not in between [1-9].");
    }
}