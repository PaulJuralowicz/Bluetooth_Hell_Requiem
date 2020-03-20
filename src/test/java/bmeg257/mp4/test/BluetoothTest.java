package test.java.bmeg257.mp4.test;

import main.java.bmeg257.mp4.arduino.Bluetooth;
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
            ArrayList<Short> data = test.fetchData();
            for (Short s:data){
                System.out.println(s);
            }
            Assert.assertTrue(data.get(2) >= 16000);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }
}
