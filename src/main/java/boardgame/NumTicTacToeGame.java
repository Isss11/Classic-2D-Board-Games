package boardgame;
import java.util.ArrayList;

public class NumTicTacToeGame extends BoardGame implements Saveable {
    private int winner;
    private int dimension;
    private String currentPlayer;
    private boolean done;
    private int turn;
    private ArrayList<String> evenNumbers = new ArrayList<String>(); // all even numbers available
    private ArrayList<String> oddNumbers = new ArrayList<String>(); // all odd numbers available
    private String stringToSave;
    private final String notPicked = "-15";

    /**
     * A constructor that creates a 3 x 3 numerical Tic-Tac-Toe game. Extends the BoardGame class.
     * @Author Isaiah Sinclair
     */
    public NumTicTacToeGame() {
        super(3,3);
        setDimension(3);

        setGrid(new NumTicTacToeGrid());

        setCurrentPlayer("Odd");
        setTurn(1);

        // setting inital version of arrays for comboboxes
        setOddNumbers(5, ((NumTicTacToeGrid) getGrid()).getTotalSquares());
        setEvenNumbers(5, ((NumTicTacToeGrid) getGrid()).getTotalSquares());
    }

    /**
     * Sets the string to save so that it could be saved to a file
     * @author Isaiah Sinclair
     */
    public void setStringToSave() {
        StringBuilder gameStringCreator = new StringBuilder();

        // adding in current player
        gameStringCreator.append(getCurrentPlayer() + "\n");

        for (int i = 1; i <= getHeight(); ++i) {
            for (int j = 1; j <= getWidth(); ++j) {
                // append only if one of these values, don't append an empty cell
                if (!getCell(i, j).equals(getNotPicked())) { // if not the invalid number 
                    gameStringCreator.append(getCell(i, j));                
                }

                if (j != getWidth()) {
                    gameStringCreator.append(",");
                }
            }

            // it appears that there is not extra line at the end of the file
            if (i != getHeight()) {
                gameStringCreator.append("\n");
            }
        }

        stringToSave = gameStringCreator.toString();
    }

    public String getStringToSave() {
        return stringToSave;
    }
    
    /**
     * Takes in a string and parses it to load the games state.
     * @param toLoad the string that will be parsed.
     */
    public void loadSavedString(String toLoad) {
        String[] stringRows;
        String[] rowValues;

        stringRows = toLoad.split("\n");

        // setting current plaer appropriate to what the file says
        setCurrentPlayer(stringRows[0]);
        setTurn(1);

        // uses remaining rows to set up the saved string
        for (int i = 1; i <= getDimension(); ++i) {
            
            // splitting up individual row elements, then loading them into the game
            rowValues = stringRows[i].split(",", -1); // limit of -1 allows for trailing spaces
            for (int j = 0; j < getDimension(); ++j) {
                // have to adjust j by one to conform to superclass
                if (rowValues[j].equals("")) {
                    getGrid().setValue(i, j + 1, "-15");
                } else {
                    setTurn(getTurn() + 1);
                    getGrid().setValue(i, j + 1, rowValues[j]);
                }
            }
        }
    }



    /**
     * Changes the current player using setCurrentPlayer()
     * @author Isaiah Sinclair
     */
    public void changePlayer() {
        if (getCurrentPlayer().equals("Odd")) {
            setCurrentPlayer("Even");
        } else {
            setCurrentPlayer("Odd");
        }
    }

    /**
     * Checks to see if there is a winning player, and it sets the winner if true.
     * @return boolean - returns true if there is a winner, false if no winner.
     */
    public boolean checkWinner() {
        for (int i = 1; i <= getDimension(); ++i) {

            // if there is a winner in a row or column, set the winner and return true
            if (checkRow(i) || checkColumn(i)) {
                if (getCurrentPlayer().equals("Odd")) {
                    setWinner(1);
                } else {
                    setWinner(2);
                }

                return true;
            }
        }

        // if there is a winner in the row or column, set the winner
        if (checkFirstDiagonal() || checkSecondDiagonal()) {
            if (getCurrentPlayer().equals("Odd")) {
                setWinner(1);
            } else {
                setWinner(2);
            }

            return true;
        }

        return false;
    }

    /**
     * Checks if there is a winner that exists (if a row adds up to 15)
     * @param row the row number to test.
     * @return true if there is a winner that exists and false if there is no winner.
     */
    private Boolean checkRow(int row) {
        int accumulatedValues;
        accumulatedValues = 0;

        for (int i = 1; i <= getDimension(); ++i) {
            accumulatedValues += Integer.parseInt(getGrid().getValue(row, i));
        }

        return checkFifteen(accumulatedValues);
    }

    /**
     * Checks if there is a winner that exists (if a column adds up to 15)
     * @param column the column number to test.
     * @return true if there is a winner that exists and false if there is no winner.
     */
    private Boolean checkColumn(int column) {
        int accumulatedValues;
        accumulatedValues = 0;

        for (int i = 1; i <= getDimension(); ++i) {
            accumulatedValues += Integer.parseInt(getGrid().getValue(i, column));
        }

        return checkFifteen(accumulatedValues);
    }

    
    /**
     * Checks to see if the diagonal that starts in the top left and ends in the bottom right has a winner.
     * @return returns true if there is a winner and false if there is no winner in that diagonal.
     */
    private Boolean checkFirstDiagonal() {
        int accumulatedValues;
        accumulatedValues = 0;

        for (int i = 1; i <= getDimension(); ++i) {
            accumulatedValues += Integer.parseInt(getGrid().getValue(i, i));
        }

        return checkFifteen(accumulatedValues);
    }

    /**
     * Checks to see if the diagonal that starts in the top right and ends in the bottom left has a winner.
     * @return returns true if there is a winner and false if there is no winner in that diagonal.
     */
    private Boolean checkSecondDiagonal() {
        int accumulatedValues;
        accumulatedValues = 0;

        for (int i = 1; i <= getDimension(); ++i) {
            // i * getDimension - 1 is the column index, i  is the row index
            accumulatedValues += Integer.parseInt(getGrid().getValue(getDimension() - i + 1, i));
        }

        return checkFifteen(accumulatedValues);
    }

    /**
     * Takes a number as an argument and returns if it is fifteen or not.
     * @param number inputted numer to check.
     * @return true or false if the inputted number is equal to 15.     
     * @author Isaiah Sinclair
     */
    private boolean checkFifteen(int number) {
        return number == 15;
    }

    
    /**
     * Takes an input and runs a single turn.
     * @param across is the amount accross the grid where we should place our input.
     * @param down is the amount down the grid where we should place our input.
     * @param input is the string to insert if the index of the grid is empty.
     * @return boolean value if the square was already set or not.
     * @author Isaiah Sinclair
     */
    @Override
    public boolean takeTurn(int across, int down, String input)  {
        // have to cast the grid to get access to teh checkIfValueSet method
        if (((NumTicTacToeGrid) getGrid()).checkIfValueSet(across, down)) {
            return false; // returning false if the turn is not set
        }

        // setting value and resetting output grid
        getGrid().setValue(across, down, input);
        ((NumTicTacToeGrid) getGrid()).setOutputGrid();
        // removing value from even or odd numbers
        removeNumber(input);

        // accumulating turn
        setTurn(getTurn() + 1);

        //checking if done
        setDone();
        if (!isDone()) {
            changePlayer();
        }

        return true;
    }

    /**
     * Takes an input and runs a single turn - calls on string version with valid conversion.
     * @param across is the amount accross the grid where we should place our input.
     * @param down is the amount down the grid where we should place our input.
     * @param input is the integer to insert if the index of the grid is empty.
     * @return boolean value if the square was already set or not.
     * @author Isaiah Sinclair
     */
    @Override
    public boolean takeTurn(int across, int down, int input) {

        return takeTurn(across + 1, down + 1, Integer.toString(input));  
        //calling the other take turn method and returning its boolean value
    }

    /**
     * Determines if the game is done or not.
     * @author Isaiah Sinclair
     */
    public void setDone() {
        if (checkWinner()) {

            if (getCurrentPlayer().equals("Odd")) {
                setWinner(1);
            } else {
                setWinner(2);
            }
            done = true;
        } else if (getTurn() > ((NumTicTacToeGrid)getGrid()).getTotalSquares()) {
            setWinner(-1);
            done = true;
        } else {
            setWinner(-1);
            done = false;
        }
    }

        /**
         * Sets the odd numbers that are available to choose for the odd player.
         * @param nOdd the total number of odd numbers.
         * @param totalSquares is total amount of squares in the board.
         */
        public void setOddNumbers(int nOdd, int totalSquares) {
            oddNumbers = new ArrayList<String>();
    
            for (int i = 0; i <= totalSquares; ++i) {
                if (i % 2 == 1) {
                    oddNumbers.add(Integer.toString(i));
                }
            }
        }
    
        public ArrayList<String> getOddNumbers() {
            return oddNumbers;
        }

       /**
        * Takes the original odd numbers array and turns it into an array to use for the combobox.
        * @return the array needed for the combobox (comboboxes are not compatiable with ArrayLists)
        * @author Isaiah Sinclair
        */
        public String[] getOddNumbersArray() {
            String[] oddNumbersArray = new String[getOddNumbers().size()];

            for (int i = 0; i < getOddNumbers().size(); ++i) {
                oddNumbersArray[i] = getOddNumbers().get(i);
            }

            return oddNumbersArray;
        }
    
        /**
         * Sets the odd numbers that are available to choose for the even player.
         * @param nOdd the total number of even numbers.
         * @param totalSquares is total amount of squares in the board.
         */
        public void setEvenNumbers(int nEven, int totalSquares) {
            evenNumbers = new ArrayList<String>();

            for (int i = 0; i <= totalSquares; ++i) {
                if (i % 2 == 0) {
                    evenNumbers.add(Integer.toString(i));
                }
            }
        }
    
        public ArrayList<String> getEvenNumbers() {
            return evenNumbers;
        }

        /**
        * Takes the original even numbers array and turns it into an array to use for the combobox.
        * @return the array needed for the combobox (comboboxes are not compatiable with ArrayLists)
        * @author Isaiah Sinclair
        */
        public String[] getEvenNumbersArray() {
            String[] evenNumbersArray = new String[getEvenNumbers().size()];

            for (int i = 0; i < getEvenNumbers().size(); ++i) {
                evenNumbersArray[i] = getEvenNumbers().get(i);
            }

            return evenNumbersArray;
        }

    /**
     * Removes a number from the arrayLists used to keep what numbers are available for the even/odd players.
     * @param number the number you want to remove.
     */
    public void removeNumber(String number) {
        // if even, search even list
        if (Integer.parseInt(number) % 2 == 0) {
            getEvenNumbers().remove(number);
        } else {
            getOddNumbers().remove(number);
        }
    }
    
    @Override
    public boolean isDone() {
        return done;
    }

    /**
     * Sets the winner, given an argument.
     * @param theWinner takes in an integer and sets that value as the corresponding player number.
     */
    public void setWinner(int theWinner) {
        winner = theWinner;
    }

    @Override
    public int getWinner() {
        return winner;
    }

    @Override
    /**
     * Returns the gameStateMessage created in the Grid class.
     * @return returns a string that represents the game state.
     * @author Isaiah Sinclair
     */
    public String getGameStateMessage() {
        return ((NumTicTacToeGrid) getGrid()).getOutputGrid().toString();
    }

    public void setDimension(int theDimension) {
        dimension = theDimension;
    }

    public int getDimension() {
        return dimension;
    }

    public void setCurrentPlayer(String theCurrentPlayer) {
        currentPlayer = theCurrentPlayer;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setTurn(int theTurn) {
        turn = theTurn;
    }

    public int getTurn() {
        return turn;
    }

    public String getNotPicked() {
        return notPicked;
    }
}
