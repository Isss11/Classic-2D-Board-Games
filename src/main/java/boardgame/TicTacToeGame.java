package boardgame;

public class TicTacToeGame extends BoardGame implements Saveable{
    private int winner;
    private int dimension;
    private String currentPlayer;
    private boolean done;
    private int turn;
    private String stringToSave;

    /**
     * A constructor that creates a 3 x 3 regular Tic-Tac-Toe game. Extends the BoardGame class.
     * @Author Isaiah Sinclair
     */
    public TicTacToeGame() {
        super(3,3);
        setDimension(3);

        setGrid(new TicTacToeGrid());

        setCurrentPlayer("X");
        setTurn(1);
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
                if (getCell(i, j).equals("X") || getCell(i, j).equals("O")) {
                    gameStringCreator.append(getCell(i, j));                
                }

                if (j != getWidth()) {
                    gameStringCreator.append(",");
                }
            }

            gameStringCreator.append("\n");
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
                getGrid().setValue(i, j + 1, rowValues[j]);

                if (rowValues[j].equals("X") || rowValues[j].equals("O")) {
                    setTurn(getTurn() + 1);
                }
            }
        }
    }

    /**
     * Changes the current player using setCurrentPlayer()
     * @author Isaiah Sinclair
     */
    public void changePlayer() {
        if (getCurrentPlayer().equals("X")) {
            setCurrentPlayer("O");
        } else {
            setCurrentPlayer("X");
        }
    }

    /**
     * Checks to see if there is a winning player, and it sets the winner if true.
     * @return boolean - returns true if there is a winner, false if no winner.
     */
    public boolean checkWinner(String player) {
        for (int i = 1; i <= getDimension(); ++i) {

            // if there is a winner in a row or column, set the winner and return true
            if (checkRow(player, i) || checkColumn(player, i)) {
                if (player.equals("X")) {
                    setWinner(1);
                } else {
                    setWinner(2);
                }

                return true;
            }
        }

        // if there is a winner in the row or column, set the winner
        if (checkFirstDiagonal(player) || checkSecondDiagonal(player)) {
            if (player.equals("X")) {
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
    private Boolean checkRow(String player, int row) {

        for (int i = 1; i <= getDimension(); ++i) {

            if (!player.equals(getGrid().getValue(i, row))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if there is a winner that exists (if a column adds up to 15)
     * @param column the column number to test.
     * @return true if there is a winner that exists and false if there is no winner.
     */
    private Boolean checkColumn(String player, int column) {
        for (int i = 1; i <= getDimension(); ++i) {
            if (!player.equals(getGrid().getValue(column, i))) {
                return false;
            }
        }

        return true;
    }

    
    /**
     * Checks to see if the diagonal that starts in the top left and ends in the bottom right has a winner.
     * @return returns true if there is a winner and false if there is no winner in that diagonal.
     */
    private Boolean checkFirstDiagonal(String player) {
        for (int i = 1; i <= getDimension(); ++i) {
            if (!player.equals(getGrid().getValue(i, i))) {
                return false;
            }
        }

        return true;
    }

    /**
    * Checks to see if the diagonal that starts in the top right and ends in the bottom left has a winner.
    * @return returns true if there is a winner and false if there is no winner in that diagonal.
    */
    private Boolean checkSecondDiagonal(String player) {
        for (int i = 1; i <= getDimension(); ++i) {

            // i * getDimension - 1 is the column index, i  is the row index
            if (!player.equals(getGrid().getValue(getDimension() - i + 1, i))) {
                return false;
            }
        }

        return true;
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
        if (((TicTacToeGrid) getGrid()).checkIfValueSet(across, down)) {
            return false; // returning false if the turn is not set
        }

        // setting value and resetting output grid
        getGrid().setValue(across, down, input);
        ((TicTacToeGrid) getGrid()).setOutputGrid();

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
        String stringInput;

        // converting integer input into string, and then calling the other takeTurn function
        if (input == 1) {
            stringInput = "X";
        } else {
            stringInput = "O";
        }

        return takeTurn(across + 1, down + 1, stringInput); //calling the other takeTurn method and returning 
        //it's boolean value
    }

    /**
     * Determines if the game is done or not.
     * @author Isaiah Sinclair
     */
    public void setDone() {
        if (checkWinner("X")) {
            setWinner(1);
            done = true;
        } else if (checkWinner("O")) {
            setWinner(2);
            done = true;
        // case where there is no winner, but all the squares are filled
        } else if (getTurn() > ((TicTacToeGrid)getGrid()).getTotalSquares()) {
            setWinner(-1);
            done = true;
        } else {
            setWinner(-1);
            done = false;
        }
    }

    // calls check winner, and sets if it is done or not
    @Override
    public boolean isDone() {
        return done;
    }

    //sets the winning player
    public void setWinner(int theWinner) {
        winner = theWinner;
    }

    @Override
    public int getWinner() {
        return winner;
    }

    @Override
    public String getGameStateMessage() {
        return ((TicTacToeGrid) getGrid()).getOutputGrid().toString();
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
}
