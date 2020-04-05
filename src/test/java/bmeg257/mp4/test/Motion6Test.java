package test.java.bmeg257.mp4.test;
import main.java.bmeg257.mp4.arduino.Motion6;
import main.java.bmeg257.mp4.arduino.Motion6Raw;
import org.junit.Assert;
import org.junit.Test;

public class Motion6Test {
    /**
     * Does motion six work? find out now!
     */
    @Test
    public void test1(){
        Motion6 test = new Motion6((short)16384,(short)16384,(short)16384,(short)16384,(short)16384, (short)16384);
        Assert.assertTrue(test.getAX()==9.81);
        Assert.assertTrue(test.getAY()==9.81);
        Assert.assertTrue(test.getAZ()==9.81);
        Assert.assertTrue(test.getGX()==125.0);
        Assert.assertTrue(test.getGY()==125.0);
        Assert.assertTrue(test.getGZ()==125.0);
    }
    /**
     * Does motion six  raw work? find out now!
     */
    @Test
    public void test2(){
        Motion6Raw test = new Motion6Raw((short)16384,(short)16384,(short)16384,(short)16384,(short)16384, (short)16384);
        Assert.assertTrue(test.getAX()==16384);
        Assert.assertTrue(test.getAY()==16384);
        Assert.assertTrue(test.getAZ()==16384);
        Assert.assertTrue(test.getGX()==16384);
        Assert.assertTrue(test.getGY()==16384);
        Assert.assertTrue(test.getGZ()==16384);
    }
}
