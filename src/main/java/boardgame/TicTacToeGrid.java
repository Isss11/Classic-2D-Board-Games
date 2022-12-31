package boardgame;

public class TicTacToeGrid extends Grid {
    private int totalSquares;
    private int dimension;
    private StringBuilder outputGrid;

    /**
     * Standard constructor for a regular Tic-Tac-Toe game.
     * @author Isaiah Sinclair
     */
    public TicTacToeGrid() {
        this(3);
    }

    /**
     * Constructor for a Tic-Tac-Toe game that can be customized to have a different dimension.
     * @param theDimension the dimension (theDimension x theDimension) of the board game.
     * @author Isaiah Sinclair
     */
    public TicTacToeGrid(int theDimension) {
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
        return getValue(across, down).equals("X") || getValue(across, down).equals("O");
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
     * Sets the default grid of the game by assigning an integer value to each square.
     * @author Isaiah Sinclair
     */
    public void setDefaultGrid() {
        int curValue;

        curValue = 0;

        for (int i = 1; i <= getDimension(); ++i) {
            for (int j = 1; j <= getDimension(); ++j) {
                setValue(i,j,Integer.toString(curValue + 1));
                ++curValue;
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
