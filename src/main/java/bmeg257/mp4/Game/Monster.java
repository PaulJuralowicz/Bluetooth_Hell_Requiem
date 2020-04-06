package main.java.bmeg257.mp4.Game;
import java.util.Random;

/**
 * A generic monster type for use in the game. The beauty of this
 * is you can make other classes that extend this type, so you can store
 * all different types of monsters in 1 list!
 */
public class Monster {
    private int health;
    private int score;
    private int attack;
    private String[] taunts;
    private String gfx;
    private Random rand;
    private String type; //typically the monsters name

    /**
     * Default constructor
     * @param health
     * @param score
     * @param attack
     * @param type
     */
    public Monster(int health, int score, int attack, String type){
        rand = new Random();
        this.health = health;
        this.score = score;
        this.attack = attack;
        this.type = type;
    }

    /**
     * All of these functions return info about the monster.
     * @return
     */

    public String display(){
        return gfx;
    }

    public int getHealth(){
        return health;
    }

    public int getScore(){
        return score;
    }

    public int getAttack(){
        return attack;
    }

    public String getRandTaunt(){
        return taunts[rand.nextInt(taunts.length)];
    }

    public String getType(){
        return this.type;
    }

    /**
     * Sets the ascii art of the monster. Only used during construction really
     * @param gfx
     */
    public void setGfx(String gfx){
        this.gfx = gfx;
    }

    /**
     * Sets the taunt list of the monster. Only used during construction really
     * @param taunts
     */
    public void setTaunts(String[] taunts){
        this.taunts = taunts;
    }

    /**
     * Returns a copy of the monster. This is to fix potential issues with mutability
     * for example, if you store 1 of each monster in a list, if you set the health of 1 to 0
     * because the player killed it, that monster is perminately dead! This will
     * let you copy the monster from the list, mess with the copy, and not mess from the master monster
     * @return
     */
    public Monster copy(){
        Monster copy = new Monster(this.health, this.score, this.attack, this.type);
        copy.setGfx(this.gfx);
        copy.setTaunts(this.taunts);
        return copy;
    }

    /**
     * Lowers the monsters health
     * @param dmg
     */
    public void lowerHealth(int dmg){
        this.health = health - dmg;
    }
}
