package main.java.bmeg257.mp4.Game;

import main.java.bmeg257.mp4.Server.Player;
import main.java.bmeg257.mp4.Server.Server;
import main.java.bmeg257.mp4.arduino.Bluetooth;
import main.java.bmeg257.mp4.arduino.Calculator;
import main.java.bmeg257.mp4.arduino.Motion6;
import main.java.bmeg257.mp4.arduino.Motion6Raw;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PhysioMaster {
    private ArrayList<Motion6Raw> hipab; //base mvmt of hip abduction
    private ArrayList<Motion6Raw> hipex; //base mvmt of hipex
    private ArrayList<Motion6Raw> seatedkneeraise; //base mvmt of seatedkneeraise
    private String userName; //user name of dude
    private String playerInput; //holds stuff from scanner
    private Scanner keyboard; // the scanner that grabs input
    private Bluetooth sensor; // bluetooth device that streams data
    private Thread server; // the server
    private Calculator calc; //thing that calcs sim

    /**
     * Constructor that initializes everything
     */
    public PhysioMaster(){
        hipab = reconstructor("local/hipab(8s).txt");
        hipex = reconstructor("local/hipex(8s).txt");
        seatedkneeraise = reconstructor("local/seatedkneeraise(5s).txt");
        keyboard = new Scanner(System.in);
        sensor = new Bluetooth();
        sensor.initialize("btspp://001403055AA3:1;authenticate=false;encrypt=false;master=false.");
        calc = new Calculator();
        startServer();
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

    private ArrayList<Motion6Raw> reconstructor(String filePath) {
        ArrayList<Motion6Raw> returnList = new ArrayList<>();
        try (Scanner motionReader = new Scanner(new FileReader(filePath))) {
            while (motionReader.hasNextLine()) {
                returnList.add(new Motion6Raw((short)motionReader.nextDouble(),(short)motionReader.nextDouble(),
                                              (short)motionReader.nextDouble(),(short)motionReader.nextDouble(),
                                              (short)motionReader.nextDouble(),(short)motionReader.nextDouble()));
            }
        } catch (IOException e) {
            System.err.println("File not found");
        } catch (NoSuchElementException e){
            System.err.println("last line empty");
        }
        return returnList;
    }

    public static void main(String args[]){
        PhysioMaster pm = new PhysioMaster();
        pm.mainMenu();
    }

    private void mainMenu(){
        System.out.println("\n" +
                "\n" +
                "  _____  _               _          \n" +
                " |  __ \\| |             (_)         \n" +
                " | |__) | |__  _   _ ___ _  ___     \n" +
                " |  ___/| '_ \\| | | / __| |/ _ \\    \n" +
                " | |    | | | | |_| \\__ \\ | (_) |   \n" +
                " |_|____|_| |_|\\__, |___/_|\\___/    \n" +
                " |__   __|      __/ || |            \n" +
                "    | |_ __ __ |___/_| | _____ _ __ \n" +
                "    | | '__/ _` |/ __| |/ / _ \\ '__|\n" +
                "    | | | | (_| | (__|   <  __/ |   \n" +
                "    |_|_|  \\__,_|\\___|_|\\_\\___|_|   \n" +
                "                                    \n");
        boolean cont = false;
        while(!cont){
            System.out.println("Hello! please enter your username (5 to 7 characters)");
            userName = keyboard.nextLine();
            if (userName.length() > 7 || userName.length() < 5){
                System.out.println("I said 5 to 7 characters >:(");
            } else {
                cont = true;
            }
        }
        cont = false;
        while(!cont){
            System.out.println("Hello " + userName + ", Please, select what you want to do");
            System.out.println("1 - Physio Regiment");
            System.out.println("2 - Game");
            System.out.println("3 - EXIT");
            playerInput = keyboard.nextLine();
            if (playerInput.equals("1")){
                physio();
            } else if (playerInput.equals("2")){
                GameMaster gm = new GameMaster(false);
                gm.start(hipab, hipex, seatedkneeraise, sensor, userName);
            } else if (playerInput.equals("3")){
                System.out.println("Goodbye!");
                return;
            } else {
                System.out.println("invalid option");
            }
        }
    }

    private void physio(){
        //TODO: VIDEO
        System.out.println("Lets do knee raise");
        System.out.println("5 reps");
        ArrayList<Motion6Raw> curr;
        System.out.println("enter t to start");
        boolean pause = true;
        while (pause){
            if (keyboard.nextLine().equals("t")){
                pause = false;
            }
        }
        int rep = 0;
        while(rep < 5){
            curr = new ArrayList<>();
            try{
                long start = System.currentTimeMillis();
                while(start + 5000 > System.currentTimeMillis()){
                    curr.add(sensor.fetchDataRaw());
                }
            } catch (IOException e){
                System.err.println("COM ERROR!");
            }
            if (calc.similarity(seatedkneeraise,curr) > 0.25){
                System.out.println("Good!");
                rep++;
            } else {
                System.out.println("not quite, try again! Reset your position and enter t to continue!");
                pause = true;
                while (pause){
                    if (keyboard.nextLine().equals("t")){
                        pause = false;
                    }
                }
            }
        }
        System.out.println("Excercise complete! Good work!");
        //TODO: VIDEO
        System.out.println("Lets do hip extension");
        System.out.println("5 reps");
        System.out.println("enter t to start");
        pause = true;
        while (pause){
            if (keyboard.nextLine().equals("t")){
                pause = false;
            }
        }
        rep = 0;
        while(rep < 5){
            curr = new ArrayList<>();
            try{
                long start = System.currentTimeMillis();
                while(start + 8000 > System.currentTimeMillis()){
                    curr.add(sensor.fetchDataRaw());
                }
            } catch (IOException e){
                System.err.println("COM ERROR!");
            }
            if (calc.similarity(hipex,curr) > 0.1){
                System.out.println("Good!");
                rep++;
            } else {
                System.out.println("not quite, try again! Reset your position and enter t to continue!");
                pause = true;
                while (pause){
                    if (keyboard.nextLine().equals("t")){
                        pause = false;
                    }
                }
            }
        }
        System.out.println("Excercise complete! Good work!");
        //TODO: VIDEO
        System.out.println("Lets do hip abduction");
        System.out.println("5 reps");
        System.out.println("enter t to start");
        pause = true;
        while (pause){
            if (keyboard.nextLine().equals("t")){
                pause = false;
            }
        }
        rep = 0;
        while(rep < 5){
            curr = new ArrayList<>();
            try{
                long start = System.currentTimeMillis();
                while(start + 8000 > System.currentTimeMillis()){
                    curr.add(sensor.fetchDataRaw());
                }
            } catch (IOException e){
                System.err.println("COM ERROR!");
            }
            double sim = calc.similarity(hipab,curr);
            System.out.println(sim);
            if (calc.similarity(hipab,curr) > 0.1){
                System.out.println("Good!");
                rep++;
            } else {
                System.out.println("not quite, try again! Reset your position and enter t to continue!");
                pause = true;
                while (pause){
                    if (keyboard.nextLine().equals("t")){
                        pause = false;
                    }
                }
            }
        }
        System.out.println("Excercise complete! Good work!");
        System.out.println("Physio complete!");
    }
}
