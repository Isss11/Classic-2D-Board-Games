package boardgame;

public class Player implements Saveable {
    private String playerName;
    private int wins;
    private int losses;
    private int gamesPlayed;
    private String stringToSave;

    /**
     * Constructor for a player. Sets values to defaults.
     * @author Isaiah Sinclair
     */
    public Player(String thePlayerName) {
        setWins(0);
        setLosses(0);
        setGamesPlayed(0);
        setPlayerName(thePlayerName);
        setStringToSave();
    }

    /**
     * Sets the string to save (this specific file format will be used when saving to a file).
     * @author Isaiah Sinclair
     */
    public void setStringToSave() {
        StringBuilder stringCreator = new StringBuilder();

        stringCreator.append(getPlayerName() + ",");
        stringCreator.append(Integer.toString(wins) + ",");
        stringCreator.append(Integer.toString(losses) + ",");
        stringCreator.append(Integer.toString(gamesPlayed));

        stringToSave = stringCreator.toString();
    }

    @Override
    public String getStringToSave() {
        return stringToSave;
    }

    @Override
    /**
     * Parses out the values of the loaded string and loads it up into the instance of the player.
     * @author Isaiah Sinclair
     */
    public void loadSavedString(String toLoad) {
        String[] parts = toLoad.split(",");
        setPlayerName(parts[0]);
        setWins(Integer.parseInt(parts[1]));
        setLosses(Integer.parseInt(parts[2]));
        setGamesPlayed(Integer.parseInt(parts[3]));
    }

    public void setWins(int theWins) {
        wins = theWins;
    }

    public int getWins() {
        return wins;
    }

    public void setLosses(int theLosses) {
        losses = theLosses;
    }

    public int getLosses() {
        return losses;
    }

    public void setGamesPlayed(int theGamesPlayed) {
        gamesPlayed = theGamesPlayed;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setPlayerName(String thePlayerName) {
        playerName = thePlayerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
