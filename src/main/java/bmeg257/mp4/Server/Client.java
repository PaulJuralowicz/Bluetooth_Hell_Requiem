package main.java.bmeg257.mp4.Server;

import java.io.*;
import java.net.Socket;

/**
 * Client that connects to the server
 */
public class Client {

    private final String host;
    private final int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    /**
     * Default constructor that does nothing
     */
    public Client() throws IOException{
        this("localhost", 50);
    }

    /**
     * Beter constructor
     * @param host string of host ip
     * @param port port of host
     * @throws IOException if something goes wrong :(
     */
    public Client(String host, int port) throws IOException{
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * sends a string to the server
     * @param toSend some non null string that is properly formated
     */
    public void send(String toSend) throws IOException{
        out.println(toSend);
        out.flush();
        closeConnection();
    }

    /**
     * sends a string to the server
     * @param request some non null string that is properly formated
     * @throws IOException if it can't read
     */
    public String recieve(String request) throws IOException{
        out.println(request);
        out.flush();
        return buildResponse();
    }

    /**
     * Builds a response from what server sends ya
     * @return the string response
     * @throws IOException if it can't read
     */
    private String buildResponse() throws IOException{
        StringBuilder inBuilder = new StringBuilder();
        while (!in.ready()) {
        }
        boolean complete = false;
        while (!complete) {
            inBuilder.append(in.readLine());
            if (inBuilder.charAt(inBuilder.length()-1) != '}') {
                inBuilder.append("\n");
            } else {
                complete = true;
            }
        }
        closeConnection();
        return inBuilder.toString();
    }

    /**
     * Closes connection to server.
     * You should do this when your done messing with the server
     * @throws IOException If something messes up.
     */
    public void closeConnection() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

}
