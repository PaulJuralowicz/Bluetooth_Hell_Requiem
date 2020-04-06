package main.java.bmeg257.mp4.Game;
import java.util.Random;

public class Monster {
    private int health;
    private int score;
    private int attack;
    private String[] taunts;
    private String gfx;
    private Random rand;
    private String type;

    public Monster(int health, int score, int attack, String type){
        rand = new Random();
        this.health = health;
        this.score = score;
        this.attack = attack;
        this.type = type;
    }

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

    public void setGfx(String gfx){
        this.gfx = gfx;
    }

    public void setTaunts(String[] taunts){
        this.taunts = taunts;
    }

    public Monster copy(){
        Monster copy = new Monster(this.health, this.score, this.attack, this.type);
        copy.setGfx(this.gfx);
        copy.setTaunts(this.taunts);
        return copy;
    }

    public String getType(){
        return this.type;
    }

    public void lowerHealth(int dmg){
        this.health = health - dmg;
    }
}
