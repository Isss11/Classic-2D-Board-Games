package boardgame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import boardgame.ui.PositionAwareButton;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import java.io.FileNotFoundException;

public class NumTicTacToeView extends JPanel {
    private String gameStatusText;
    private JLabel gameStatusHeader;
    private NumTicTacToeGame game;
    private PositionAwareButton[][] buttons;
    private PositionAwareButton clickedButton;
    private int clickedAcross;
    private int clickedDown;
    private JLabel gameTurnHeader;
    private int newGameResponse;
    private GameUI gameFrame;
    private JComboBox<String> evenComboBox;
    private JComboBox<String> oddComboBox;
    private JPanel comboBoxesPanel;
    private CardLayout comboBoxesLayout;

    /**
     * Constructor for the graphical component of the TicTacToeView of the object.
     * @param theGameFrame takes in the super class as a parameter to be able th change it based on 
     * certain events within this class.
     * @author Isaiah Sinclair
     */
    public NumTicTacToeView(GameUI theGameFrame) {
        super();
        setGameFrame(theGameFrame);

        setLayout(new BorderLayout());

        // setting up the actual game
        setGame(new NumTicTacToeGame());

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
        add(setComboBoxesPanel(), BorderLayout.EAST);
        
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
                JOptionPane.showMessageDialog(getGameFrame(), "An error occurred while saving to the file.");
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

                // resetting the comboboxes based on the loaded input
                loadComboBoxes();

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
     * Sets the panel that holds the comboboxes for the Numerical TicTacToe game.
     * Only one combobox shows at a time.
     * @return a jpanel with the comboboxes.
     */
    public JPanel setComboBoxesPanel() {
        comboBoxesPanel = new JPanel();
        setComboBoxesLayout(new CardLayout());
        comboBoxesPanel.setLayout(getComboBoxesLayout());

        // setting the combo boxes given an ArrayList that is converted to an array
        setOddComboBox(getGame().getOddNumbersArray());
        setEvenComboBox(getGame().getEvenNumbersArray());
        comboBoxesPanel.add(getOddComboBox());
        comboBoxesPanel.add(getEvenComboBox());

        return comboBoxesPanel;
    }

    public JPanel getComboBoxesPanel() {
        return comboBoxesPanel;
    }

    public void setComboBoxesLayout(CardLayout theComboBoxesLayout) {
        comboBoxesLayout = theComboBoxesLayout;
    }

    public CardLayout getComboBoxesLayout() {
        return comboBoxesLayout;
    }

    public void setOddComboBox(String[] theOddNumbers) {
        oddComboBox = new JComboBox<>(theOddNumbers);
    }

    public JComboBox<String> getOddComboBox() {
        return oddComboBox;
    }

    public void setEvenComboBox(String[] theEvenNumbers) {
        evenComboBox = new JComboBox<>(theEvenNumbers);
    }

    public JComboBox<String> getEvenComboBox() {
        return evenComboBox;
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
     * Checks to see if the game is finished and displays the corresponding results (ex. "Player 1 won!") 
     * if there is a winner.
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
            if (getGame().getCurrentPlayer().equals("Odd")) {
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
            getGameFrame().getSaveGameItem().setEnabled(false);
            getGameFrame().getLoadGameItem().setEnabled(false);
            getGameFrame().chooseGame();
        } else {
            getGameFrame().startNumTicTacToe();
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
    private void enterMove(ActionEvent e) { 
        boolean validClick;
        String input;
        //finding index of button that was clicked and setting them
        setClickedButton(e);
        findClickedButton();
        // this is getting the selected value upon a turn (from the comboBox)
        if (getGame().getCurrentPlayer().equals("Odd")) {
            input = getOddComboBox().getSelectedItem().toString();
            validClick = getGame().takeTurn(getClickedAcross(), getClickedDown(), input);
        } else {
            input = getEvenComboBox().getSelectedItem().toString();
            validClick = getGame().takeTurn(getClickedAcross(), getClickedDown(), input);  
        }
        if (validClick) {
            setGameStatusText("A move was made.");
            getComboBoxesLayout().next(getComboBoxesPanel());
            updateComboBox(input);
        } else {
            setGameStatusText("Please pick a box that is not picked!");
        }   
        getGameStatusHeader().setText(getGameStatusText());
        setButtonGrid();

        if (validClick) {
            checkGameState();
        }
    }

    /**
     * Iterates through grid and resets the comboboxes correspondingly.
     * @author Isaiah Sinclair
     */
    public void loadComboBoxes() {
        // removing current comboboxes (to reset)
        getComboBoxesPanel().remove(getOddComboBox());
        getComboBoxesPanel().remove(getEvenComboBox());

        getGame().setEvenNumbers(5, getGame().getDimension() * getGame().getDimension());
        getGame().setOddNumbers(5, getGame().getDimension() * getGame().getDimension());

        // removing elements of the array
        for (int i = 1; i <= getGame().getDimension(); ++i) {
            for (int j = 1; j <= getGame().getDimension(); ++j) {
                // if even
                if (Integer.parseInt(getGame().getCell(i, j)) % 2 == 0) {
                    getGame().getEvenNumbers().remove(getGame().getCell(i, j));
                } else { // odd number case
                    getGame().getOddNumbers().remove(getGame().getCell(i, j)); 
                }
            }
        }
        // resetting combo boxes and adding them back to the panel
        setOddComboBox(getGame().getOddNumbersArray());
        setEvenComboBox(getGame().getEvenNumbersArray());
        getComboBoxesPanel().add(getOddComboBox()); // odd is added first since that's how it was originally
        getComboBoxesPanel().add(getEvenComboBox());
        repaint();
        revalidate();
        if (getGame().getCurrentPlayer().equals("Even")) { // if the current player is Even, switch to its combobox
            getComboBoxesLayout().next(getComboBoxesPanel());
        }
    }

    /**
     * Resets the corresponding combobox when a value is input.
     * @param input user input
     * @author Isaiah Sinclair
     */
    public void updateComboBox(String input) {
        if (Integer.parseInt(input) % 2 == 0) {
            getComboBoxesPanel().remove(getEvenComboBox());
            setEvenComboBox(getGame().getEvenNumbersArray());
            getComboBoxesPanel().add(getEvenComboBox());
        } else {
            getComboBoxesPanel().remove(getOddComboBox());
            setOddComboBox(getGame().getOddNumbersArray());
            getComboBoxesPanel().add(getOddComboBox());
        }

        revalidate();
        repaint();
    }

    /**
     * Sets the button grid with it's current values.
     * @author Isaiah Sinclair
     */
    public void setButtonGrid() {
        for (int i = 0; i < getGame().getWidth(); ++i) {
            for (int j = 0; j < getGame().getHeight(); ++j) {
                // if the value is not set, make sure to not display it's associated value
                if (getGame().getCell(i + 1, j + 1) == getGame().getNotPicked()) {
                    getButtonsElement(i, j).setText(" ");
                } else {
                    getButtonsElement(i, j).setText(getGame().getCell(i + 1, j + 1));
                }
            }
        }
    }

    /**
     * Finds the specific indices of the clicked button, and sets those with setClickedAcross and setClickedDown
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

    public void setGame(NumTicTacToeGame theGame) {
        game = theGame;
    }

    public NumTicTacToeGame getGame() {
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