package main.java.bmeg257.mp4.Server;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client that connects to the server
 */
public class Client {

    private final String host;
    private final int port;
    /**
     * Default constructor that does nothing
     */
    public Client(){
        this("localhost", 50);
    }

    /**
     * Beter constructor
     * @param host string of host ip
     * @param port port of host
     */
    public Client(String host, int port){
        this.host = host;
        this.port = port;
    }

    /**
     * sends a string to the server
     * @param toSend some non null string that is properly formated
     */
    public void send(String toSend){
        try {
            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println(toSend);
            out.flush();
            socket.close();
        } catch (Exception e) {
            System.out.println("CONNECTION FAILED");
        }
    }

}
