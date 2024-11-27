/*
 * Author: Andreas Sotiras
 * SERVER CLASS... Will work once other pieces are implemented.
 */
import java.io.*;
import java.net.*;

public class Server {
    private BookManager bookManager;

    public Server(BookManager bookManager) {
        this.bookManager = bookManager;
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, bookManager).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}