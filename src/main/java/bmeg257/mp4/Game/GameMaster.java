package main.java.bmeg257.mp4.Game;

import main.java.bmeg257.mp4.Server.Client;
import main.java.bmeg257.mp4.Server.Server;

import java.io.IOException;
import java.util.Scanner;

/**
 * TODO: THE GAME
 */
public class GameMaster {
    private Scanner keyboard; //read player input
    private Client leaderboard; //connect to server
    private String playerInput; //general player input string
    private Thread server; //For debugging mostly, if the server needs to be made.2

    public static void main(String args[]){
        GameMaster game = new GameMaster(true);
        game.start();
    }

    public GameMaster(boolean startServer){
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

    public void start(){
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

    private void mainMenu(){
        System.out.println("Hello travelar, please select your path! \n");
        System.out.println("1 - ENTER THE DUNGEON");
        System.out.println("2 - VIEW FAMOUS HEROES");
        System.out.println("3 - EXIT");
        playerInput = keyboard.nextLine();
        if (playerInput.equals("1")){
            enterDungeon();
        } else if (playerInput.equals("2")){
            displayLeaderBoard();
        } else if (playerInput.equals("3")){
            System.out.println("Goodbye!");
            return;
        } else {
            System.out.println("Ehh? I didn't catch that");
            mainMenu();
        }
    }

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

    private void enterDungeon(){
        System.out.println("WIP");
        mainMenu();
    }
}
