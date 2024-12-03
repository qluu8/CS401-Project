/*
 * Author: Andreas Sotiras
 * SERVER CLASS... Will work once other pieces are implemented.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private BookManager bookManager;
    private LoanManager loanManager;
    private registration registration;

    public Server(BookManager bookManager, LoanManager loanManager, registration registration) {
        this.bookManager = bookManager;
        this.loanManager = loanManager;
        this.registration = registration;
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket, bookManager, loanManager, registration).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Save books to file on server shutdown
            bookManager.saveBooksToFile();
        }
    }

    public static void main(String[] args) {
        BookManager bookManager = new BookManager();
        LoanManager loanManager = new LoanManager();
        registration registration = new registration();

        Server server = new Server(bookManager, loanManager, registration);
        server.start(8080); // Start the server on port 8080
    }
}
