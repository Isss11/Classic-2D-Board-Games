package boardgame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.FileNotFoundException;
import java.io.IOException;

import boardgame.ui.PositionAwareButton;

public class TicTacToeView extends JPanel {
    private String gameStatusText;
    private JLabel gameStatusHeader;
    private TicTacToeGame game;
    private PositionAwareButton[][] buttons;
    private PositionAwareButton clickedButton;
    private int clickedAcross;
    private int clickedDown;
    private JLabel gameTurnHeader;
    private int newGameResponse;
    private GameUI gameFrame;

    /**
     * Constructor for the graphical component of the TicTacToeView of the object.
     * @param theGameFrame takes in the super class as a parameter to be able th change it based on 
     * certain events within this class.
     * @author Isaiah Sinclair
     */
    public TicTacToeView(GameUI theGameFrame) {
        super();
        setGameFrame(theGameFrame);

        setLayout(new BorderLayout());

        // setting up the actual game
        setGame(new TicTacToeGame());

        // adding in game text and board
        setGameStatusText("Let the Game Begin!");
        addGameElements();
    }

    /**
     * Adds the needed elements of the TicTacToeView
     * @author Isaiah Sinclair
     */
    public void addGameElements() {
        setGameStatusHeader();
        setGameTurnHeader("Player " + getGame().getCurrentPlayer() + ", it is your turn.");

        add(getGameStatusHeader(), BorderLayout.SOUTH);
        add(makeButtonGrid(getGame().getWidth(), getGame().getHeight()), BorderLayout.CENTER);
        add(getGameTurnHeader(), BorderLayout.NORTH);
        
        setButtonGrid();

        // configuring the menu items that haven't been adjusted for this game yet
        configureLoadOption();
        configureSaveOption();
    }

    /**
     * This configures the 'save game menu option'
     * @author Isaiah Sinclair
     */
    public void configureSaveOption() {
        getGameFrame().removeJMenuItemListeners(getGameFrame().getSaveGameItem());

        getGameFrame().getSaveGameItem().setEnabled(true);
        getGameFrame().getSaveGameItem().addActionListener(ev -> saveGame());
    }

    /**
     * This is the higher level method that saves a file to a game.
     * @author Isaiah Sinclair
     */
    public void saveGame() {
        boolean validFile = false;
        while (!validFile) {
            try {
                getGameFrame().setSaveFilePath();
                getGame().setStringToSave();
                SaveInfo.save(getGame(), getGameFrame().getFilePath(), "assets");
                validFile = true;
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Saving was cancelled.");
                validFile = true;

                return;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(getGameFrame(), "You input an invalid file path."
                +" Please pick a file path in the assets directory.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(getGameFrame(), "An error occurred while saving to the file..");
            }
        }
    }

    /**
     * This configures the 'load game menu option'
     * @author Isaiah Sinclair
     */
    public void configureLoadOption() {
        getGameFrame().removeJMenuItemListeners(getGameFrame().getLoadGameItem());

        getGameFrame().getLoadGameItem().setEnabled(true);
        getGameFrame().getLoadGameItem().addActionListener(ev -> loadGame());
    }

    /**
     * This function is a higher level function that loads a file to the game.
     * @author Isaiah Sinclair
     */
    public void loadGame() {

        boolean validFile = false;
        while (!validFile) {
            try {
                getGameFrame().setLoadFilePath();
                SaveInfo.load(getGame(), getGameFrame().getFilePath(), "assets");

                setButtonGrid();
                checkGameState();

                validFile = true;
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(getGameFrame(), "Loading was cancelled.");
                validFile = true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(getGameFrame(), "You input an invalid file path."
                +" Please pick a file path in the assets directory.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(getGameFrame(), "An error occurred while loading the file.");
            }
        }
    }

    /**
     * Makes the button grid to interact with the game.
     * @param tall the height of the button grid.
     * @param wide the width of the button grid.
     * @return returns a JPanel containing the button grid.
     * @Author Judi McCuaig (we were allowed to use this in our program).
     */
    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        buttons = new PositionAwareButton[tall][wide];
        panel.setLayout(new GridLayout(wide, tall));
        for (int y=0; y < wide; y++) {
            for (int x=0; x < tall; x++) { 
                //Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x+1); //made the choice to be 1-based
                buttons[y][x].setDown(y+1);
                buttons[y][x].addActionListener(e -> {
                    enterMove(e);});

                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

    /**
     * Checks to see if the game is finished and displays the corresponding results 
     * (ex. "Player 1 won!") if there is a winner.
     * @author Isaiah Sinclair
     */
    public void checkGameState() {
        if (getGame().isDone()) {
            if (getGame().getWinner() == 1 || getGame().getWinner() == 2) {
                getGameTurnHeader().setText("Player " + getGame().getCurrentPlayer() + " won!");
                disableButtons();
            } else {
                getGameTurnHeader().setText("No one won!");
            }

            updatePlayerProfiles();

            // prompting to ask if there should be a new game
            setNewGameResponse();
            resetGame();

            setGameStatusText("Please play again.");
            getGameStatusHeader().setText(getGameStatusText());
        } else {
            getGameTurnHeader().setText("Player " + getGame().getCurrentPlayer() + ", it is your turn.");
        }
    }

    /**
     * This updates the two player profiles if the game is finished.
     * @author Isaiah Sinclair
     */
    public void updatePlayerProfiles() {
        // Update game status
        // accumulating games played

        getGameFrame().getPlayerOne().setGamesPlayed(getGameFrame().getPlayerOne().getGamesPlayed() + 1);
        getGameFrame().getPlayerTwo().setGamesPlayed(getGameFrame().getPlayerTwo().getGamesPlayed() + 1);

        // if a winner exists
        if (getGame().getWinner() == 1 || getGame().getWinner() == 2) {
            if (getGame().getCurrentPlayer().equals("X")) {
                    getGameFrame().getPlayerOne().setWins(getGameFrame().getPlayerOne().getWins() + 1);
                    getGameFrame().getPlayerTwo().setLosses(getGameFrame().getPlayerTwo().getLosses() + 1);
            } else {
                getGameFrame().getPlayerOne().setLosses(getGameFrame().getPlayerOne().getLosses() + 1);
                getGameFrame().getPlayerTwo().setWins(getGameFrame().getPlayerTwo().getWins() + 1);
            }
        }
    }

    /**
     * Uses the user input from the newGameResponse to set-up the next step of the GUI.
     * @author Isaiah Sinclair
     */
    public void resetGame() {
        if (getNewGameResponse() == JOptionPane.NO_OPTION) {
            getGameFrame().chooseGame();

            getGameFrame().getSaveGameItem().setEnabled(false);
            getGameFrame().getLoadGameItem().setEnabled(false);
        } else {
            getGameFrame().startTicTacToe();
        }
    }

    /**
     * Sets newGameResponse to see if the user wants to play another game (sets it with a comboBox)
     * @author Isaiah Sinclair
     */
    public void setNewGameResponse() {
        newGameResponse = JOptionPane.showConfirmDialog(this, "Do you want to play another game?", 
        "New Game Choice", JOptionPane.YES_NO_OPTION);
    }

    private int getNewGameResponse() {
        return newGameResponse;
    }

    /**
     * Disables all the buttons on the button grid.
     * @author Isaiah Sinclair
     */
    public void disableButtons() {
        for (int i = 0; i < getGame().getWidth(); ++i) {
            for (int j = 0; j < getGame().getHeight(); ++j) {
                getButtonsElement(i, j).setEnabled(false);
            }
        }
    }

    /**
     * Triggered upon clicking a button, enters the user's move using takeTurn.
     * @param e the event information.
     * @author Isaiah Sinclair
     */
    public void enterMove(ActionEvent e) { 
        boolean validClick;

        //finding index of button that was clicked and setting them
        setClickedButton(e);
        findClickedButton();

        validClick = getGame().takeTurn(getClickedAcross(), getClickedDown(), getGame().getCurrentPlayer());

        if (validClick) {
            setGameStatusText("A move was made.");
        } else {
            setGameStatusText("Please pick a box that is not picked!");
        }   

        // now resetting board and game status text according to new values
        getGameStatusHeader().setText(getGameStatusText());
        setButtonGrid();

        // if it is a valid click, check the games state again
        if (validClick) {
            checkGameState();
        }
    }

    /**
     * Sets the button grid with it's current values.
     * @author Isaiah Sinclair
     */
    public void setButtonGrid() {
        for (int i = 0; i < getGame().getWidth(); ++i) {
            for (int j = 0; j < getGame().getHeight(); ++j) {
                if (getGame().getCell(i + 1, j + 1).equals("X") || getGame().getCell(i + 1, j + 1).equals("O")) {
                    getButtonsElement(i, j).setText(getGame().getCell(i + 1, j + 1));
                }   else {
                    getButtonsElement(i, j).setText(" ");
                }
            }
        }
    }

    /**
     * Finds the specific indices of the clicked button, and sets those with 
     * setClickedAcross and setClickedDown
     * @author Isaiah Sinclair
     */
    public void findClickedButton() {
        int i;
        int j;

        // going to use for loop to find relevant button clicked (its indices)
        for (i = 0; i < getGame().getHeight(); ++i) {
            for (j = 0; j < getGame().getWidth(); ++j) {
                if (getButtonsElement(i, j) == getClickedButton()) {
                    setClickedAcross(i + 1);
                    setClickedDown(j + 1); // adding one to match the specification of grid
                }
            }
        }
    }

    /**
     * Sets the JLabel that says who's turn it is.
     * @param theGameTurnText the text to be stated in the label.
     * @author Isaiah Sinclair
     */
    public void setGameTurnHeader(String theGameTurnText) {
        gameTurnHeader = new JLabel(theGameTurnText);
    }

    public JLabel getGameTurnHeader() {
        return gameTurnHeader;
    }

    public void setGameStatusText(String theGameStatusText) {
        gameStatusText = theGameStatusText;
    }

    public String getGameStatusText() {
        return gameStatusText;
    }

    public void setGameStatusHeader() {
        gameStatusHeader = new JLabel(getGameStatusText());
    }

    public JLabel getGameStatusHeader() {
        return gameStatusHeader;
    }

    public void setGame(TicTacToeGame theGame) {
        game = theGame;
    }

    public TicTacToeGame getGame() {
        return game;
    }

    public void setClickedButton(ActionEvent e) {
        clickedButton = ((PositionAwareButton)(e.getSource()));
    }

    public PositionAwareButton getClickedButton() {
        return clickedButton;
    }

    public PositionAwareButton getButtonsElement(int i, int j) {
        return buttons[i][j];
    }

    public void setClickedAcross(int theClickedAcross) {
        clickedAcross = theClickedAcross;
    }

    public int getClickedAcross() {
        return clickedAcross;
    }

    public void setClickedDown(int theClickedDown) {
        clickedDown = theClickedDown;
    }

    public int getClickedDown() {
        return clickedDown;
    }

    public void setGameFrame(GameUI theGameFrame) {
        gameFrame = theGameFrame;
    }

    public GameUI getGameFrame() {
        return gameFrame;
    }
}