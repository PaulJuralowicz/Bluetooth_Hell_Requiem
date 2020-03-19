package test.java.bmeg257.mp4.test;

import main.java.bmeg257.mp4.Server.Client;
import main.java.bmeg257.mp4.Server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import org.junit.Assert;
import org.junit.Test;

public class ServerTest {

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

    /**
     * The most basic of tests. Sees if a client can connect and get a response. Manually check if it worked, dummy
     */
    @Test
    public void test1() {
        startServer();
        try {
            Client client = new Client("localhost", 4949);
            client.send("{test}");
            long curr = System.currentTimeMillis();
            while (System.currentTimeMillis() < curr + 2000) {
            }
        } catch (Exception e) {
            System.out.println("NO");
            Assert.fail();
        }
    }
}
