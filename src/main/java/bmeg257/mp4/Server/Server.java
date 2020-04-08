package main.java.bmeg257.mp4.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

/**
 * Server will take in client sent data and write it to a text file. Nothing too crazy.
 *
 * Fun fact, most of this is copied from the final project of CPEN 221 where I had to make a server!
 * Luckily, that thing was fairly comprehensive so I can modifying a lot.
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
    private static final String FAMOUS_HEROES = "local/leaderboard.txt";
    private static final String FOLDER_PATH = "local";
    private ArrayList<Hero> leaderboard = new ArrayList<>();
    private static final int LEADERBOARD_SIZE = 10;

    /**
     * Abstract functions:
     * SeverSocket is the socket the server uses, with its specific port
     * maxClients is the number of concurrent requests the server can complete
     * currClients is the number of clients currently connected
     * FILE_PATH is path to a data log
     * FAMOUT_HEROES is path to the leaderboard WOO
     * FOLDER_PATH is just the path to the folder that holds all the stuff.
     * leaderboard is the ordered leader board of big boy players.
     * LEADERBOARD_SIZE is the size of the leaderboard the server will send
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
        restoreLeaderboard();
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
     * Restores the leaderboard, from a file!
     */
    private void restoreLeaderboard() {
        if (new File(FOLDER_PATH).list().length > 1) {
            try (Scanner leaderboardReader = new Scanner(new FileReader(FAMOUS_HEROES))) {
                String username;
                int score;
                while (leaderboardReader.hasNextLine()) {
                    username = leaderboardReader.next();
                    score = leaderboardReader.nextInt();
                    leaderboard.add(new Hero(username, score));
                }
                leaderboard.sort((x,y) -> Integer.compare(y.getScore(),x.getScore()));
            } catch (IOException e) {
                System.err.println("File not found");
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
     * Handle one client connection. Returns when it is done.
     * Yes it is messy, and should probably handle each case in its own function, but whatever.
     * I am sorry, Sathish Sensei
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
        }if (input.equals("{leaderboardGet}")){
            System.err.println("SENDING FAMOUS HEROES");
            StringBuilder leaderBuilder = new StringBuilder();
            for (int i = 0; i < LEADERBOARD_SIZE && i < leaderboard.size(); i++){
                leaderBuilder.append((i+1) + ": " + leaderboard.get(i).getUsername() + "\t\t\t" + leaderboard.get(i).getScore() + "\n");
            }
            out.write("{" + leaderBuilder.toString() + "}");
            out.flush();
        } if (input.contains("leaderboardput")) {
            StringBuilder username = new StringBuilder();
            for (int i = 18; input.charAt(i) != ' '; i++){
                username.append(input.charAt(i));
            }
            StringBuilder score = new StringBuilder();
            for(int i = 18 + username.length() + 3; input.charAt(i) != '}';i++){
                score.append(input.charAt(i));
            }
            leaderboard.add(new Hero(username.toString(), Integer.parseInt(score.toString())));
            leaderboard.sort((x,y) -> Integer.compare(y.getScore(),x.getScore()));
            try (FileWriter leaderWriter = new FileWriter(FAMOUS_HEROES, true)) {
                leaderWriter.write("\n");
                leaderWriter.write(username.toString());
                leaderWriter.write("\n");
                leaderWriter.write(score.toString());
                System.err.println("data written");
            } catch (IOException e) {
                System.err.println("ERROR, HEROES FILE CANT BE OPENED");
            }

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
