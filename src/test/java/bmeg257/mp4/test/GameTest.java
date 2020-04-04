package test.java.bmeg257.mp4.test;

import main.java.bmeg257.mp4.Game.GameMaster;
import main.java.bmeg257.mp4.Server.Client;
import main.java.bmeg257.mp4.Server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;

public class GameTest {

    /**
     * Starts a test server!
     */
    public void startServer() {
        Thread server = new Thread(new Runnable() {
            Server s;

            @Override
            public void run() {

                Server s = new Server(4949, 3);
            }
        });
        if (!server.isAlive()) {
            server.start();
        }
    }
}
