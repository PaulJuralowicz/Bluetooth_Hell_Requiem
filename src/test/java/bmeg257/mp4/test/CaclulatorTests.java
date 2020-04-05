package test.java.bmeg257.mp4.test;
import main.java.bmeg257.mp4.arduino.Calculator;
import main.java.bmeg257.mp4.arduino.Motion6Raw;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class CaclulatorTests {

    /**
     * Test sim function
     */
    @Test
    public void test1(){
        Calculator test = new Calculator();
        ArrayList<Motion6Raw> a = new ArrayList<>();
        a.add(new Motion6Raw((short)0,(short)0,(short)0,(short)0,(short)0,(short)0));
        a.add(new Motion6Raw((short)1,(short)1,(short)1,(short)1,(short)1,(short)1));
        a.add(new Motion6Raw((short)2,(short)2,(short)2,(short)2,(short)2,(short)2));
        a.add(new Motion6Raw((short)3,(short)3,(short)3,(short)3,(short)3,(short)3));

        ArrayList<Motion6Raw> b = new ArrayList<>();
        b.add(new Motion6Raw((short)0,(short)0,(short)0,(short)0,(short)0,(short)0));
        b.add(new Motion6Raw((short)1,(short)1,(short)1,(short)1,(short)1,(short)1));
        b.add(new Motion6Raw((short)2,(short)2,(short)2,(short)2,(short)2,(short)2));
        b.add(new Motion6Raw((short)3,(short)3,(short)3,(short)3,(short)3,(short)3));
        Assert.assertEquals(test.similarity(a,b), 1, 0.0000001);
    }
}
