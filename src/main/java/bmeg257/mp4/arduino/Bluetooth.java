package main.java.bmeg257.mp4.arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.util.ArrayList;


/**
 * This class facilitaes the collection of raw data from the arduino. Basicaly, it spoofs a serial port.
 * This is the RAW data, so your gonna get a bunch of 16 bit signed integers. FUN
 */
public class Bluetooth {
    byte[] data = new byte[200]; //buffer for input stream
    private OutputStream os; //stream of bytes sent to arduino
    private InputStream is; //stream if byte recieved
    private StringBuilder dataGatherer = new StringBuilder(); // String builder that helps deal with data.
    private ArrayList<Integer> currData = new ArrayList<>(); //list of integers that is the acutal data
    /**
     * currData order: AX, AY, AZ, GX, GY, GZ
     */

    /**
     * A constructor
     * @param url the address of bluetooth device you wish to connect to
     */
    public static void initialize(String url) {
        try {
            new Bluetooth().go(url);
        } catch (Exception ex) {
            Logger.getLogger(Bluetooth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Attempts to connect to bluetooth device
     * @param url url of bluetooth device
     */
    private void go(String url){
        try {
            StreamConnection streamConnection = (StreamConnection)
                    Connector.open(url);
            os = streamConnection.openOutputStream();
            is = streamConnection.openInputStream();
        } catch (Exception e) {
            System.out.println("Connection failed, try again");
            go(url);
        }
    }

    /**
     * Fetches data from arduino appon request
     *
     * THIS IS DANGEROUS AS IT RETURNS THE LIST AS IS, SO IT IS
     * MUTABLE. TAKE CARE TO COPY THE LIST LATER.
     * @return A list of accelerations and gyroscope values, ordered in AX AY AZ GX GY GZ
     * @throws IOException if one of the streams fails
     */
    public ArrayList<Integer> fetchData() throws IOException {
        currData.clear(); //clear data list
        os.write("1".getBytes()); //ask for data
        while(is.available() == 0); //wait for input

        return currData;
    }
}