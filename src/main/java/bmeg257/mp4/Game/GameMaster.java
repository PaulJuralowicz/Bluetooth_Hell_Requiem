package main.java.bmeg257.mp4.Game;

import main.java.bmeg257.mp4.Server.Client;
import main.java.bmeg257.mp4.Server.Server;
import main.java.bmeg257.mp4.arduino.Bluetooth;
import main.java.bmeg257.mp4.arduino.Calculator;
import main.java.bmeg257.mp4.arduino.Motion6Raw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * TODO: THE GAME
 */
public class GameMaster {
    private ArrayList<Motion6Raw> hipab; //base mvmt of hip abduction
    private ArrayList<Motion6Raw> hipex; //base mvmt of hipex
    private ArrayList<Motion6Raw> seatedkneeraise; //base mvmt of seatedkneeraise
    private Bluetooth sensor; // bluetooth device that streams data
    private Scanner keyboard; //read player input
    private Client leaderboard; //connect to server
    private String playerInput; //general player input string
    private Calculator calc; //calcs how good your heals and attacks are!
    private Thread server; //For debugging mostly, if the server needs to be made.
    private String username; //username of user, whose name is the username. User's name.

    public GameMaster(boolean startServer){
        calc = new Calculator();
        keyboard = new Scanner(System.in);
        try {
            if(startServer) startServer();
            leaderboard = new Client();
        } catch (IOException e){
            System.err.println("ERROR, UNABLE TO CONNECT TO SERVER");
        }
    }

    private void startServer(){
        server = new Thread(new Runnable() {
            Server s;

            @Override
            public void run() {
                Server s = new Server(50, 3);
            }
        });
        if (!server.isAlive()) {
            server.start();
        }
    }

    /**
     * Initialize some stuff, display a cool ascii art of the title
     * @param hipab             base for motion hip abduction
     * @param hipex             base for motion hip extension
     * @param seatedkneeraise   base for motion seated knee raise
     * @param sensor            the bluetooth
     * @param username          username for highscore!
     */

    public void start(ArrayList<Motion6Raw> hipab, ArrayList<Motion6Raw> hipex,
                      ArrayList<Motion6Raw> seatedkneeraise, Bluetooth sensor, String username){
        this.hipab = hipab;
        this.hipex = hipex;
        this.seatedkneeraise = seatedkneeraise;
        this.sensor = sensor;
        this.username = username;
        System.out.print("\n" +
                "\n" +
                "  _______ _    _ ______    _____ _____            _   _ _____                             \n" +
                " |__   __| |  | |  ____|  / ____|  __ \\     /\\   | \\ | |  __ \\                            \n" +
                "    | |  | |__| | |__    | |  __| |__) |   /  \\  |  \\| | |  | |                           \n" +
                "    | |  |  __  |  __|   | | |_ |  _  /   / /\\ \\ | . ` | |  | |                           \n" +
                "    | |  | |  | | |____  | |__| | | \\ \\  / ____ \\| |\\  | |__| |                           \n" +
                "  __|_|  |_|  |_|______|__\\_____|_| _\\_\\/_/ ___\\_\\_| \\_|_____/_  _____ ______ ____  _   _ \n" +
                " |  __ \\| |  | \\ \\   / / ____|_   _/ __ \\  |  __ \\| |  | | \\ | |/ ____|  ____/ __ \\| \\ | |\n" +
                " | |__) | |__| |\\ \\_/ / (___   | || |  | | | |  | | |  | |  \\| | |  __| |__ | |  | |  \\| |\n" +
                " |  ___/|  __  | \\   / \\___ \\  | || |  | | | |  | | |  | | . ` | | |_ |  __|| |  | | . ` |\n" +
                " | |    | |  | |  | |  ____) |_| || |__| | | |__| | |__| | |\\  | |__| | |___| |__| | |\\  |\n" +
                " |_|    |_|  |_|  |_| |_____/|_____\\____/  |_____/ \\____/|_| \\_|\\_____|______\\____/|_| \\_|\n" +
                "                                                                                          \n" +
                "                                                                                          \n" +
                "\n");
        mainMenu();
    }

    /**
     * The main menu interface
     */

    private void mainMenu(){
        System.out.println("Hello travelar, please select your path! \n");
        System.out.println("1 - ENTER THE DUNGEON");
        System.out.println("2 - VIEW FAMOUS HEROES");
        System.out.println("3 - EXIT DUNGEON");
        playerInput = keyboard.nextLine();
        if (playerInput.equals("1")){
            enterDungeon();
        } else if (playerInput.equals("2")){
            displayLeaderBoard();
        } else if (playerInput.equals("3")){
            System.out.println("Goodbye, travaller!\n");
            return;
        } else {
            System.out.println("Ehh? I didn't catch that");
            mainMenu();
        }
    }

    /**
     * Displayes the leadboard after talking to the server
     */
    private void displayLeaderBoard(){
        try {
            StringBuilder toDisplay = new StringBuilder();
            toDisplay.append(leaderboard.recieve("{leaderboardGet}"));
            toDisplay.deleteCharAt(0);
            toDisplay.deleteCharAt(toDisplay.length()-1);
            System.out.println("Here, are the grandest adventurers!\n"+ "Hero\t\t\tPlunder\n"+toDisplay.toString());
            System.out.println("Press 1 to return to the main menu!");
            while(true){
                if (keyboard.nextLine().equals("1")){
                    mainMenu();
                }
            }
        } catch (Exception e){
            System.err.println("ERROR, COULD NOT COMMUNICATE WITH SERVER");
            mainMenu();
        }
    }

    /**
     * A big function that does the entire dungeon game
     */
    private void enterDungeon(){
        //setup
        int playerHealth = 10;
        int score = 0;
        int room = 0;
        Random rand = new Random();
        ArrayList<Monster> enemy = new ArrayList<>();
        ArrayList<String> excerciseList = new ArrayList<>();
        excerciseList.add("Seated Knee Raise");
        excerciseList.add("Hip Abduction");
        excerciseList.add("Hip Extension");
        enemy.add(new Goblin());
        enemy.add(new Tony());
        Monster roomEnemy;
        System.out.println("You enter the dungeon, what horrors, or treasures, will you find?");
        pause(500);
        boolean dead = false;
        //this is the main game loop that keeps looping till you die
        while(!dead){
            //select random monster, copy it
            room++;
            System.out.println("You enter room " + room +"...");
            pause(1000);
            roomEnemy = enemy.get(rand.nextInt(enemy.size())).copy();
            System.out.println("Suddenly, a " + roomEnemy.getType() + " Appears");
            System.out.println(roomEnemy.display());
            System.out.println("\"" + roomEnemy.getRandTaunt() + "\" It shouts!");
            pause(1000);
            //the battle loop, keeps going till you or monster die :(
            while(roomEnemy.getHealth() > 0 && !dead){
                System.out.println("Player Health: " + playerHealth + "\tEnemy Health: " + roomEnemy.getHealth() +"\tGold: " + score);
                System.out.println("What will you do? \n");
                System.out.println("1 - Attack");
                System.out.println("2 - Heal");
                playerInput = keyboard.nextLine();
                if (playerInput.equals("1")){
                    System.out.println("You attack!");
                    String attack = excerciseList.get(rand.nextInt(excerciseList.size()));
                    //selected a random excercise, now you gotta do it to attack
                    if (attack.equals("Seated Knee Raise")){
                        System.out.println("Complete 1 "+ attack +" to attack the monster!");
                        int damage = (int) (4 * evalKnee());
                        System.out.println("Excellent attack! " + damage + " damage done!");
                        roomEnemy.lowerHealth(damage);
                    }
                    if (attack.equals("Hip Abduction")){
                        System.out.println("Complete 1 "+ attack +" to attack the monster!");
                        int damage = (int) (4 * evalHipab());
                        System.out.println("Excellent attack! " + damage + " damage done!");
                        roomEnemy.lowerHealth(damage);
                    }
                    if (attack.equals("Hip Extension")){
                        System.out.println("Complete 1 "+ attack +" to attack the monster!");
                        int damage = (int) (4 * evalHipex());
                        System.out.println("Excellent attack! " + damage + " damage done!");
                        roomEnemy.lowerHealth(damage);
                    }
                } else if (playerInput.equals("2")) {
                    System.out.println("You heal!");
                    String attack = excerciseList.get(rand.nextInt(excerciseList.size()));
                    //selected a random excercise, now do it to heal
                    if (attack.equals("Seated Knee Raise")){
                        System.out.println("Complete 1 "+ attack +" to heal!");
                        int damage = (int) (4 * evalKnee());
                        System.out.println("Excellent heal! " + damage + " damage healed!");
                        playerHealth += damage;
                    }
                    if (attack.equals("Hip Abduction")){
                        System.out.println("Complete 1 "+ attack +" to heal!");
                        int damage = (int) (4 * evalHipab());
                        System.out.println("Excellent heal! " + damage + " damage healed!");
                        playerHealth += damage;
                    }
                    if (attack.equals("Hip Extension")){
                        System.out.println("Complete 1 "+ attack +" to heal!");
                        int damage = (int) (4 * evalHipex());
                        System.out.println("Excellent heal! " + damage + " damage healed!");
                        playerHealth += damage;
                    }
                }
                pause(2000);
                System.out.println(roomEnemy.display());
                System.out.println("\"" + roomEnemy.getRandTaunt() + "\"");
                System.out.println("The mosnter attacks! "+ roomEnemy.getAttack() + " damage dealt!");
                playerHealth -= roomEnemy.getAttack();
                if (playerHealth <= 0) {
                    dead = true;
                }
            }
            // you escaped the battle loop, with you or monster being dead. If it is monster that died
            // add its score to total score, otherwise, don't
            if (!dead){
                System.out.println("The monster is vanquished! You find " + roomEnemy.getScore() +" gold in the room!");
                score += roomEnemy.getScore();
            }
        }
        //You died, but lets keep this PG 13, game is over, total score is displayed as well as room number
        pause(500);
        System.out.println("Woah! Luckily I found you traveler, looks like you made it all the way to room "
                           + room +"\nwith " + score +" Gold! thats a good haul!\nLet me write it down on the leaderboard!");
        try{
            //upload score to leaderboard
            leaderboard.send("{leaderboardput U:" + username +" S:" + score + "}");
        } catch (IOException e){
            System.out.println("Oops, looks like I am out of ink (server connection failed)");
        }
        mainMenu();
    }

    /**
     * Next 3 methods are for healing/damage dealing purpose, calc how well you did the motion.
     * @return
     */
    private double evalKnee(){
        System.out.println("enter t to start");
        boolean pause = true;
        while (pause){
            if (keyboard.nextLine().equals("t")){
                pause = false;
            }
        }
        ArrayList<Motion6Raw> curr = new ArrayList<>();
        try{
            long start = System.currentTimeMillis();
            while(start + 5000 > System.currentTimeMillis()){
                curr.add(sensor.fetchDataRaw());
            }
        } catch (IOException e){
            System.err.println("COM ERROR!");
        }
        return calc.similarity(seatedkneeraise,curr)/0.4;
    }

    private double evalHipab(){
        System.out.println("enter t to start");
        boolean pause = true;
        while (pause){
            if (keyboard.nextLine().equals("t")){
                pause = false;
            }
        }
        ArrayList<Motion6Raw> curr = new ArrayList<>();
        try{
            long start = System.currentTimeMillis();
            while(start + 8000 > System.currentTimeMillis()){
                curr.add(sensor.fetchDataRaw());
            }
        } catch (IOException e){
            System.err.println("COM ERROR!");
        }
        return calc.similarity(hipab,curr)/0.25;
    }

    private double evalHipex(){
        System.out.println("enter t to start");
        boolean pause = true;
        while (pause){
            if (keyboard.nextLine().equals("t")){
                pause = false;
            }
        }
        ArrayList<Motion6Raw> curr = new ArrayList<>();
        try{
            long start = System.currentTimeMillis();
            while(start + 8000 > System.currentTimeMillis()){
                curr.add(sensor.fetchDataRaw());
            }
        } catch (IOException e){
            System.err.println("COM ERROR!");
        }
        return calc.similarity(hipex,curr)/0.25;
    }

    /**
     * to give people time to read, the game pauses. This function does it.
     * @param millis
     */
    private void pause(long millis){
        long start = System.currentTimeMillis();
        while(start + millis > System.currentTimeMillis()){}
    }
}
