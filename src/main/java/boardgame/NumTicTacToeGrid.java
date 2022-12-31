package boardgame;

public class NumTicTacToeGrid extends Grid {
    private int totalSquares;
    private int dimension;
    private StringBuilder outputGrid;

    /**
     * Standard constructor for a numerical Tic-Tac-Toe game.
     * @author Isaiah Sinclair
     */
    public NumTicTacToeGrid() {
        this(3);
    }

    /**
     * Constructor for a numerical Tic-Tac-Toe game that can be customized to have a different dimension.
     * @param theDimension the dimension (theDimension x theDimension) of the board game.
     * @author Isaiah Sinclair
     */
    public NumTicTacToeGrid(int theDimension) {
        //creating an instance of grid 
        super(theDimension, theDimension);
        setDimension(theDimension);
        setTotalSquares();
        emptyGrid(); //sets all values to a default " "
        setDefaultGrid();
        setOutputGrid();
        
    }

    /**
     * Checks if a value is set on the board.
     * @param across the amount across the board.
     * @param down the amount down the board.
     * @return returns true if the value is set, false if not.
     */
    public boolean checkIfValueSet(int across, int down) {
        return !getValue(across, down).equals("-15");
    }

    /**
     * Calcualtes the total number of squares in the grid..
     * @author Isaiah Sinclair
     */
    public void setTotalSquares() {
        totalSquares = getHeight() * getWidth();
    }

    public int getTotalSquares() {
        return totalSquares;
    }

    /**
     * Sets teh default grid of the game by assigning an integer value to each square.
     * @author Isaiah Sinclair
     */
    public void setDefaultGrid() {

        for (int i = 1; i <= getDimension(); ++i) {
            for (int j = 1; j <= getDimension(); ++j) {
                setValue(i,j, "-15"); // negative one represents no entry
            }
        }
    }

    /**
     * Creates a new StringBuilder each time, that is the string representation of the grid
     * (to print in TextUI).
     * @author Isaiah Sinclair
     */
    public void setOutputGrid() {

        outputGrid = new StringBuilder();

        for (int i = 1; i <= getDimension(); ++i) {
            outputGrid.append(" | ");
            for (int j = 1; j <= getDimension(); ++j) {
                outputGrid.append(getValue(i, j));

                outputGrid.append(" | ");
            }

            if (i != getDimension()) {
                outputGrid.append("\n");
            }
        }
    }

    public StringBuilder getOutputGrid() {
        return outputGrid;
    }

    public void setDimension(int theDimension) {
        dimension = theDimension;
    }

    public int getDimension() {
        return dimension;
    }
}
