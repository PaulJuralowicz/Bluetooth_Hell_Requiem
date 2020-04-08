package main.java.bmeg257.mp4.arduino;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * RECORDS A MOTION FOR PLAYBACK
 */
public class Recorder {

    public static void main(String args[]){
        Bluetooth recordingDevice = new Bluetooth();
        recordingDevice.initialize("btspp://001403055AA3:1;authenticate=false;encrypt=false;master=false.");
        Motion6Raw current;
        Scanner keyboard = new Scanner(System.in);
        try (FileWriter excerciseWriter = new FileWriter("local/hipab(8s).txt")) {
            System.out.println("Enter t to start");
            ArrayList<Motion6Raw> recording = new ArrayList<>();
            boolean pause = true;
            while (pause){
                if (keyboard.nextLine().equals("t")){
                    pause = false;
                }
            }
            long start = System.currentTimeMillis();
            while(start + 8000 > System.currentTimeMillis()){
                recording.add(recordingDevice.fetchDataRaw());
            }
            for(Motion6Raw s : recording){
                excerciseWriter.write(Double.toString(s.getAX()));
                excerciseWriter.write("\n");
                excerciseWriter.write(Double.toString(s.getAY()));
                excerciseWriter.write("\n");
                excerciseWriter.write(Double.toString(s.getAZ()));
                excerciseWriter.write("\n");
                excerciseWriter.write(Double.toString(s.getGX()));
                excerciseWriter.write("\n");
                excerciseWriter.write(Double.toString(s.getGY()));
                excerciseWriter.write("\n");
                excerciseWriter.write(Double.toString(s.getGZ()));
                excerciseWriter.write("\n");
            }
            System.out.println("DONE");
        } catch (IOException e){
            System.out.println("ERR");
        }
    }

}
