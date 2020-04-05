package main.java.bmeg257.mp4.arduino;

/**
 * This beefy boy will get ya a displacement.
 */

public class Calculator {

    private Bluetooth sensor; //the bluetooth connection to the arduino
    private Motion6 current;
    private static final String btURL = "btspp://001403055AA3:1;authenticate=false;encrypt=false;master=false."; //default BT URL

    /**
     * Constructor, starts the bluetooth and everything
     */
    public Calculator(){
        sensor = new Bluetooth();
        sensor.initialize(btURL);
    }
}
