package main.java.bmeg257.mp4.Server;

/**
 * Stores a username and a score!
 */
public class Hero {
    private final String username;
    private final int score;

    /**
     * Default constructor
     * @param username the user's name (DUH)
     * @param score    the user's score
     */
    public Hero(String username, int score){
        this.score = score;
        this.username = username;
    }

    /**
     * Returns the username
     * @return the username of the dude
     */
    public String getUsername(){
        return username;
    }

    /**
     * returns the score
     * @return the score of the dude
     */
    public int getScore(){
        return score;
    }

}
