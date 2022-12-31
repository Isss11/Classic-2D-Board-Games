package boardgame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class GameUI extends JFrame{
    private String gameTitle;
    private JButton startButton;
    private int panelMargin;
    private JPanel gameScreen;
    private JPanel choicePanel;
    private JPanel gameContainer;
    private String gameName;
    private JMenuBar saveMenuBar;
    private Player playerOne;
    private Player playerTwo;
    private String filePath;
    private JMenu saveMenu;
    private JMenuItem savePlayerOne;
    private JMenuItem loadPlayerOne;
    private JMenuItem savePlayerTwo;
    private JMenuItem loadPlayerTwo;
    private JMenuItem saveGameItem;
    private JMenuItem loadGameItem;

    /**
     * Starts up the GameUI, allows the user to pick their game.
     * @param args command line argumentw
     * @author Isaiah Sinclair
     */
    public static void main(String[] args) {
        GameUI userInterface = new GameUI("Sinclair Strategy Games");
        userInterface.chooseGame();
    }

    /**
     * Constructor for the GameUI
     * @param theGameTitle a string that I used to put at the top of the GameUI
     */
    public GameUI(String theGameTitle) {
        // setting up UI
        super();
        setGameTitle(theGameTitle);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        setSaveMenuBar();
        add(getSaveMenuBar(), BorderLayout.NORTH);

        // setting players
        setPlayerOne(new Player("Player One"));
        setPlayerTwo(new Player("Player Two"));
    }

    /**
     * Sets the default game container when called by the game constructor.
     * @param theGameName the name of the game - used to put at the top of the graphics window.
     * @author Isaiaih Sinclair
     */
    public void setDefaultGameLayouts(String theGameName) {
        setGameName(theGameName);
        getGameContainer().removeAll();
    }

    /**
     * This method removes all the action listeners associated with a given JMenuITem
     * @param eventItem a JMenuItem that I will remove the event listeners from.
     * @author Isaiah Sinclair
     */
    public void removeJMenuItemListeners(JMenuItem eventItem) {
        ActionListener[] listeners = eventItem.getActionListeners();

        for (ActionListener l : listeners) {
            eventItem.removeActionListener(l);
        }
    }

    /**
     * Starts up a Numerical Tic-Tac-Toe game and puts GUI version of it in the game container.
     * @author Isaiah Sinclair
     */
    public void startNumTicTacToe() {

        setDefaultGameLayouts("Numerical Tic-Tac-Toe");

        getGameContainer().add(new NumTicTacToeView(this));

        // re-drawing GUI
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * Starts up a regular Tic-Tac-Toe game and puts the graphics version of it in the game container.
     * @author Isaiah Sinclair
     */
    public void startTicTacToe() {
        setDefaultGameLayouts("Tic-Tac-Toe");
        
        getGameContainer().add(new TicTacToeView(this));

        // re-drawing GUI
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * This takes in a player as a paramter and saves it to a file.
     * @param playerToSave is the player object to save.
     * @parm location is the string of the location ("assets")
     * @author Isaiah Sinclair
     */
    public void savePlayerToFile(Player playerToSave, String location) {
        boolean validFile = false;
        while (!validFile) {
            try {
                setSaveFilePath();
                playerToSave.setStringToSave();
                SaveInfo.save(playerToSave, getFilePath(), location);
                validFile = true;
            } catch (FileNotFoundException e) { // if cancelled
                JOptionPane.showMessageDialog(this, "Loading was cancelled.");
                validFile = true;
                return;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "You input an invalid file path."
                +" Please pick a file path in the assets directory.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occured while trying to save the file.");
            }
        }

        JOptionPane.showMessageDialog(this, "Successfully saved at " + getFilePath() + ".");
    }

    /**
     * Loads in file info into a given player instance.
     * @param playerToLoad the player to load information into.
     * @param location the location of the file (ex. assets)
     * @author Isaiah Sinclair
     */
    public void loadPlayerToFile(Player playerToLoad, String location) {
        boolean validFile = false;
        while (!validFile) {
            try {
                setLoadFilePath();
                SaveInfo.load(playerToLoad, getFilePath(), location);
                validFile = true;
            } catch (FileNotFoundException e) { // if cancelled
                JOptionPane.showMessageDialog(this, "Loading was cancelled.");
                validFile = true;
                return;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "You input an invalid file path."
                +" Please pick a file path in the assets directory.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "An error occured while trying to load the file.");
            }
        }

        JOptionPane.showMessageDialog(this, "Successfully loaded from " + getFilePath() + ".");
    }

    /**
     * Starts up screen where the user can choose which game they want to play.
     * @author Isaiah Sinclair
     */
    public void chooseGame() {
        if (getGameScreen() != null) {
            getGameScreen().removeAll();
        }

        repaint();
        pack();

        createGameContainer();
        setGameScreen();
        add(getGameScreen(), BorderLayout.CENTER);
        repaint();
        pack();
    }

    /**
     * This gets a given file path with a JFileChooser.
     * @author Isaiah Sinclair
     */
    public void setSaveFilePath() throws Exception {
        String[] absoluteFilePath;  

        JFileChooser fileChooser = new JFileChooser("assets");

        int status = fileChooser.showSaveDialog(this);

        // basically just getting the final part of the file path (after assets) to use for my relative path

        try {
            absoluteFilePath = fileChooser.getSelectedFile().getPath().split("assets");
            filePath = absoluteFilePath[1].substring(1); // cuts off initial slash to get relative path
        } catch (Exception e) {
            if (status == JFileChooser.CANCEL_OPTION) {
                throw new FileNotFoundException();
            } else { // throws a filenotfound exception if the cancel option is chosen
                throw new IOException();
            }
        }
    }

    /**
     * This sets the given file path with a JFileChooser (used for loading functions)
     * @author Isaiah Sinclair
     */
    public void setLoadFilePath() throws Exception {
        String[] absoluteFilePath;  

        JFileChooser fileChooser = new JFileChooser("assets");

        int status = fileChooser.showOpenDialog(this);

        // basically just getting the final part of the file path (after assets) to use for my relative path

        try {
            absoluteFilePath = fileChooser.getSelectedFile().getPath().split("assets");
            filePath = absoluteFilePath[1].substring(1); // cuts off initial slash to get relative path
        } catch (Exception e) {
            if (status == JFileChooser.CANCEL_OPTION) {
                throw new FileNotFoundException();
            } else { // in case the user clicks 'cancel'
                throw new IOException();
            }
        }
    }

    public String getFilePath() {
        return filePath;
    }
    /**
     * Creates initial version of the game container that will hold the [Game]View instance.
     * @author Isaiah Sinclair
     */
    public void createGameContainer() {
        gameContainer = new JPanel(); 
        gameContainer.setLayout(new BorderLayout());
        gameContainer.add(new JLabel("Pick a Game to Play!"), BorderLayout.CENTER);
    }

    public JPanel getGameContainer() {
        return gameContainer;
    }

    /**
     * Creates a menu item to save player one.
     * @author Isaiah Sinclair
     */
    public void setSavePlayerOne() {
        savePlayerOne = new JMenuItem("Save");
        savePlayerOne.addActionListener(ev -> savePlayerToFile(getPlayerOne(), "assets"));
    }

    public JMenuItem getSavePlayerOne() {
        return savePlayerOne;
    }

    /**
     * Creates a menu item to save player two.
     * @author Isaiah Sinclair
     */
    public void setSavePlayerTwo() {
        savePlayerTwo = new JMenuItem("Save");
        savePlayerTwo.addActionListener(ev -> savePlayerToFile(getPlayerTwo(), "assets"));
    }

    public JMenuItem getSavePlayerTwo() {
        return savePlayerTwo;
    }

    /**
     * Sets the menuItem for load player one.
     * @author Isaiah Sinclair
     */
    public void setLoadPlayerOne() {
        loadPlayerOne = new JMenuItem("Load");
        loadPlayerOne.addActionListener(ev -> loadPlayerToFile(getPlayerOne(), "assets"));
    }

    public JMenuItem getLoadPlayerOne() {
        return loadPlayerOne;
    }

    /**
     * Sets the menuItem for load player two.
     * @author Isaiah Sinclair
     */
    public void setLoadPlayerTwo() {
        loadPlayerTwo = new JMenuItem("Load");
        loadPlayerTwo.addActionListener(ev -> loadPlayerToFile(getPlayerTwo(), "assets"));
    }

    public JMenuItem getLoadPlayerTwo() {
        return loadPlayerTwo;
    }

    /**
     * Sets the save game item menu item, with no action listener and disabled by default.
     * @author Isaiah Sinclair
     */
    public void setSaveGameItem() {
        saveGameItem = new JMenuItem("Save");
        saveGameItem.setEnabled(false);
    }

    public JMenuItem getSaveGameItem() {
        return saveGameItem;
    }

    /**
     * Sets the load game menu item with no action listener and disabled by default.
     * @author Isaiah Sinclair
     */
    public void setloadGameItem() {
        loadGameItem = new JMenuItem("Load");
        loadGameItem.setEnabled(false);
    }

    public JMenuItem getLoadGameItem() {
        return loadGameItem;
    }

    /**
     * Sets the menu items and adds their event listeners
     * @author Isaiah Sinclair
     */
    public void setSaveMenuItems() {
        setSavePlayerOne();
        setSavePlayerTwo();
        setLoadPlayerOne();
        setLoadPlayerTwo();
        setSaveGameItem();
        setloadGameItem();
    }

    /**
     * Creates the menu used in the dropdown menu for saving and loading players.
     * @author Isaiah Sinclair
     */
    public void setSaveMenu() {
        saveMenu = new JMenu("Save/Load");
        // creating submenus
        JMenu playerOneMenu = new JMenu("Player One");
        JMenu playerTwoMenu = new JMenu("Player Two");

        // this menu doesn't have any even listeners mapped to it originally
        JMenu gameOptionsMenu = new JMenu("Game");

        // setting menu items
        setSaveMenuItems();
        
        playerOneMenu.add(getSavePlayerOne());
        playerOneMenu.add(getLoadPlayerOne());

        playerTwoMenu.add(getSavePlayerTwo());
        playerTwoMenu.add(getLoadPlayerTwo());

        gameOptionsMenu.add(getSaveGameItem());
        gameOptionsMenu.add(getLoadGameItem());

        saveMenu.add(playerOneMenu);
        saveMenu.add(playerTwoMenu);
        saveMenu.add(gameOptionsMenu);
    }

    public JMenu getSaveMenu() {
        return saveMenu;
    }

    /**
     * Creates the drop-down menu bar for saving/loading players.
     * @Author Isaiah Sinclair
     */
    public void setSaveMenuBar() {
        saveMenuBar = new JMenuBar();
        setSaveMenu();
        saveMenuBar.add(getSaveMenu());
    }

    public JMenuBar getSaveMenuBar() {
        return saveMenuBar;
    }

    /**
     * Sets the game screen that holds the new game buttons and the game container.
     * @Author Isaiah Sinclair
     */
    public void setGameScreen() {

        gameScreen = new JPanel();
        gameScreen.setLayout(new BorderLayout());

        setChoicePanel();

        gameScreen.add(getGameContainer(), BorderLayout.CENTER);
        gameScreen.add(getChoicePanel(), BorderLayout.SOUTH);

    }

    public JPanel getGameScreen() {
        return gameScreen;
    }

    /**
     * Sets the choice panel where the user can choose between the games.
     * @Author Isaiah Sinclair
     */
    public void setChoicePanel() {
        JButton numTicTacToeButton;
        JButton ticTacToeButton;

        choicePanel = new JPanel();
        choicePanel.setLayout(new FlowLayout());

        numTicTacToeButton = new JButton("Numerical Tic-Tac-Toe");
        ticTacToeButton = new JButton("Tic-Tac-Toe");

        numTicTacToeButton.addActionListener(e->startNumTicTacToe());
        ticTacToeButton.addActionListener(e->startTicTacToe());

        choicePanel.add(numTicTacToeButton);
        choicePanel.add(ticTacToeButton);
    }

    public JPanel getChoicePanel() {
        return choicePanel;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setGameTitle(String theGameTitle) {
        gameTitle = theGameTitle;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public int getPanelMargin() {
        return panelMargin;
    }

    public void setGameName(String theGameName) {
        gameName = theGameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setPlayerOne(Player thePlayerOne) {
        playerOne = thePlayerOne;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerTwo(Player thePlayerTwo) {
        playerTwo = thePlayerTwo;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }
}