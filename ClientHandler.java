/*
 * Author: Andreas Sotiras 11/27/24
 * Client handler. This will show how to call the client if the server is running.
 * THIS NEEDS UPDATING... 
 */

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private BookManager bookManager;
    private LoanManager loanManager;
    private registration registration;

    public ClientHandler(Socket socket, BookManager bookManager, LoanManager loanManager, registration registration) {
        this.clientSocket = socket;
        this.bookManager = bookManager;
        this.loanManager = loanManager;
        this.registration = registration;
    }

    @Override
    public void run() {
        System.out.println("Client connected: " + clientSocket.getInetAddress());

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Welcome to the Library System!");
            out.println("Choose your interface:");
            out.println("1. Console");
            out.println("2. GUI");
            out.println("Enter your choice:");

            String choice = in.readLine();

            if ("1".equals(choice)) {
                out.println("You have chosen the Console interface.");
                handleConsoleInterface(out, in);
            } else if ("2".equals(choice)) {
                out.println("You have chosen the GUI interface.");
            } else {
                out.println("Invalid choice. Disconnecting.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleConsoleInterface(PrintWriter out, BufferedReader in) throws IOException {
        out.println("New users: type 'register' to begin the account-making process.");
        out.println("Returning users: type 'login' to log in.");
        out.println("Type 'exit' to disconnect.");
        String clientRequest;

        while ((clientRequest = in.readLine()) != null) {
            System.out.println("Received: " + clientRequest);

            String command = clientRequest.trim().toLowerCase();
            String response;

            switch (command) {
                case "login":
                    response = handleLogin(out, in);
                    break;
                case "register":
                    response = registration.registerUser(out, in);
                    break;
                case "exit":
                    out.println("Goodbye!");
                    System.out.println("Client disconnected: " + clientSocket.getInetAddress());
                    return;
                default:
                    response = "Unknown command. Available commands: login, register, and exit.";
            }

            out.println(response);
        }
    }

    private String handleLogin(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter your username:");
        String username = in.readLine().trim();

        out.println("Enter your password:");
        String password = in.readLine().trim();

        String role = registration.validateUser(username, password);

        if (role != null) {
            out.println("Login successful! Role: " + role);

            if ("STAFF".equalsIgnoreCase(role)) {
                StaffAccess staffAccess = new StaffAccess();
                staffAccess.handleStaffMenu(bookManager, loanManager, username, out, in);
            } else if ("PUBLIC".equalsIgnoreCase(role)) {
                PublicAccess publicAccess = new PublicAccess();
                publicAccess.handlePublicMenu(bookManager, loanManager, username, out, in);
            }

            return username + " logged out. Enter login details or type 'exit'.";
        } else {
            return "Invalid username or password. Please try again.";
        }
    }
}
