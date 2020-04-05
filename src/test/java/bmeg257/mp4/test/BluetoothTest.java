package test.java.bmeg257.mp4.test;

import main.java.bmeg257.mp4.arduino.Bluetooth;
import main.java.bmeg257.mp4.arduino.Motion6;
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
}
