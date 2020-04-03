package main.java.bmeg257.mp4.Server;

import java.io.IOException;
import java.util.List;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

/**
 * Server will take in client sent data and write it to a text file. Nothing too crazy.
 */

public class Server {
    /**
     * Default port number where the server listens for connections.
     */
    public static final int DEFAULT_PORT = 2772;
    private ServerSocket serverSocket;
    private AtomicInteger maxClients;
    private AtomicInteger currClients = new AtomicInteger(0);
    private static final String FILE_PATH = "local/data.txt";

    /**
     * Abstract functions:
     * SeverSocket is the socket the server uses, with its specific port
     * maxClients is the number of concurrent requests the server can complete
     * currClients is the number of clients currently connected
     */

    /**
     * Rep invariant: serverSocket != null
     * maxClients > 0
     * 0 <= currClients <= maxClients
     */

    public Server() {
        this(50, 50);
    }

    /**
     * Thread safety arguements:
     * The thread it runs is good:
     * - max and current clients are atomic integers.
     *
     * For a threads that all access this data type, firstly why it is just threading
     * a thread, secondly it should be fine as all instance variables are either atomic, set
     * and never mutated, or thread safe on their own.
     */

    /**
     * Start a server at a given port number, with the ability to process
     * upto n requests concurrently.
     *
     * @param port the port number to bind the server to
     *             must be between 0 and 65535 inclusive
     * @param n    the number of concurrent requests the server can handle
     */
    public Server(int port, int n) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalArgumentException("BAD SOCKET");
        }
        if (n < 1) {
            throw new IllegalArgumentException();
        } else {
            maxClients = new AtomicInteger(n);
            try {
                serve();
            } catch (IOException e) {
                System.out.println("The server socket is broken, you fool.");
            }
        }
    }

    /**
     * Run the server, listening for connections and handling them.
     *
     * @throws IOException if the main server socket is broken
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            final Socket socket = serverSocket.accept();
            // create a new thread to handle that client
            Thread handler = new Thread(new Runnable() {
                public void run() {
                    try {
                        try {
                            while (currClients.get() >= maxClients.get()) {
                            } //wait till there is a spot
                            handle(socket);
                        } finally {
                            socket.close();
                            currClients.decrementAndGet();
                        }
                    } catch (IOException ioe) {
                        // this exception wouldn't terminate serve(),
                        // since we're now on a different thread, but
                        // we still need to handle it
                        ioe.printStackTrace();
                    }
                }
            });
            // start the thread
            handler.start();
        }
    }

    /**
     * Handle one client connection. Returns when it is done writing to file
     *
     * @param socket socket where client is connected
     * @throws IOException if connection encounters an error
     */

    private void handle(Socket socket) throws IOException {
        currClients.incrementAndGet();
        System.err.println("client connected");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        while (!in.ready()) {
        }
        StringBuilder inBuilder = new StringBuilder();
        while (inBuilder.length() == 0 || inBuilder.charAt(inBuilder.length() - 1) != '}') {
            inBuilder.append(in.readLine());
        }
        String input = inBuilder.toString();
        if (input.equals("{PING}")){
            System.err.println("PING REQUEST RECIEVED");
            out.write("{PONG}");
            out.flush();
        } else {
            try (FileWriter queriesWriter = new FileWriter(FILE_PATH, true)) {
                queriesWriter.write(input);
                queriesWriter.write("\n");
                System.err.println("data written");
            } catch (IOException e) {
                System.err.println("ERROR, QUERIES FILE CANT BE OPENED");
            }
        }
        in.close();
        out.close();
        socket.close();
    }
}
