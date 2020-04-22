package test.java.bmeg257.mp4.test;

import main.java.bmeg257.mp4.arduino.Bluetooth;
import main.java.bmeg257.mp4.arduino.Motion6;
import main.java.bmeg257.mp4.arduino.Motion6Raw;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BluetoothTest {
    @Test
    public void testBT(){
        String hc05Url =
                "btspp://001403055AA3:1;authenticate=false;encrypt=false;master=false.";
        Bluetooth test = new Bluetooth();
        test.initialize(hc05Url);
        try{
            Motion6 data = test.fetchData();
            System.out.println(data.getAX()+ "\t" +data.getAY()+ "\t" +data.getAZ()+ "\t" +
                               data.getGX()+ "\t" +data.getGY()+ "\t" +data.getGZ());
            Assert.assertTrue(data.getAZ() > 9.81);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    /**
     * Lets see how fast we can go fellas
     */
    @Test
    public void testBTSPEED(){
        //connect
        String hc05Url =
                "btspp://001403055AA3:1;authenticate=false;encrypt=false;master=false.";
        Bluetooth test = new Bluetooth();
        test.initialize(hc05Url);
        try{
            ArrayList<Motion6Raw> testList = new ArrayList<>();
            long start = System.currentTimeMillis();
            for(int i = 0; i < 60; i++){
                //fetch 60 data packet, see how long it takes
                testList.add(test.fetchDataRaw());
            }
            Assert.assertTrue(System.currentTimeMillis() - start < 1000);
            System.out.println(System.currentTimeMillis() - start);
            //to calc exact data just see how long it actually took.
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    /**
     * A displacement test
     *
     * Now, you might be thinking "Hey! This was uploaded late!"
     * Well, I originally did this test in excell. For completeness, I added a java version of this. WOO
     */
    @Test
    public void testBTAccuracy(){
        //connect
        String hc05Url =
                "btspp://001403055AA3:1;authenticate=false;encrypt=false;master=false.";
        Bluetooth test = new Bluetooth();
        test.initialize(hc05Url);
        try{
            ArrayList<Motion6> acc = new ArrayList<>();
            long start = System.currentTimeMillis();
            //RECORD MOTION OVER 2 SECONDS
            while(System.currentTimeMillis() < start + 2000){
                acc.add(test.fetchData());
            }
            ArrayList<Double> vel = new ArrayList<>();
            ArrayList<Double> dis = new ArrayList<>();
            //Assume motion was entirely in y-dir (hint it was if you did the test correctly)
            //Time to integrate, twice! We use  trapazoid rule
            vel.add(0.0);
            double  temp;
            double deltaT = 2000.0 / acc.size(); // time between each sample
            for (int i = 1; i < acc.size();  i++){
                temp = vel.get(i-1) + acc.get(i-1).getAY()*deltaT + 0.5*(acc.get(i).getAY() - acc.get(i-1).getAY())*deltaT;
                vel.add(temp);
            }
            dis.add(0.0);
            for (int i = 1; i < vel.size();  i++){
                temp = dis.get(i-1) + vel.get(i-1)*deltaT + 0.5*(vel.get(i) - vel.get(i-1))*deltaT;
                dis.add(temp);
            }
            Assert.assertEquals(0.1, dis.get(dis.size()-1),0.05); //Compares the two doubles, with a delta of 0.05. This is in meters, so the delta is scaled as you would think
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }
}
