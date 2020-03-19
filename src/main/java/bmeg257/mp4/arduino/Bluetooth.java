package main.java.bmeg257.mp4.arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;


/**
 * This class facilitaes the collection of raw data from the arduino. Basicaly, it spoofs a serial port.
 * This is the RAW data, so your gonna get a bunch of 16 bit signed integers. FUN
 */
public class Bluetooth {
    byte[] data = new byte[200];
    boolean scanFinished = false;
    public static void initialize(String url) {
        try {
            new Bluetooth().go(url);
        } catch (Exception ex) {
            Logger.getLogger(Bluetooth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void go(String url) throws Exception {
        try {
            StreamConnection streamConnection = (StreamConnection)
                    Connector.open(url);
            OutputStream os = streamConnection.openOutputStream();
            InputStream is = streamConnection.openInputStream();
            while(true){
                fetchData(os,is);
            }
        } catch (Exception e) {
            System.out.println("Connection failed, try again");
            go(url);
        }
    }

    private void fetchData(OutputStream os, InputStream is) throws IOException, InterruptedException {
        os.write("1".getBytes());
        Thread.sleep(1000);/*
        is.read(data);
        System.out.print("AX AY AZ GX GY GZ");
        System.out.println();
        for (int i = 0; i < 6; i++){
            System.out.print(data[i]);
            System.out.print("\t");
        }
        System.out.println("");*/
        System.out.println(is.read());
    }
}
