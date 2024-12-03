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

            out.println("Welcome to the Library Server!");
            out.println("New users: type 'register' to begin the account-making process.");
            out.println("Returning users: type 'login;username;password'");
            out.println("Example: login;publicuser;apple123");
            String clientRequest;

            // Continuously listen for client requests
            while ((clientRequest = in.readLine()) != null) {
                System.out.println("Received: " + clientRequest);

                // Split the input into command parts
                String[] parts = clientRequest.split(";");
                String command = parts[0].toLowerCase();

                // Handle the command
                String response = handleCommand(command, parts, out, in);
                out.println(response);

                // If the client sends "exit", close the connection
                if ("exit".equals(command)) {
                    System.out.println("Client disconnected: " + clientSocket.getInetAddress());
                    break;
                }
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

    // Command Handler
    private String handleCommand(String command, String[] parts, PrintWriter out, BufferedReader in) {
        try {
            switch (command) {
                case "login":
                    return handleLogin(parts, out, in); // Pass all required arguments

                case "register":
                    return registration.registerUser(out, in); // Handle user registration

                case "exit":
                    return "Goodbye!";

                default:
                    return "Unknown command. Available commands: login, register, and exit.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing command: " + e.getMessage();
        }
    }



    private String handleLogin(String[] parts, PrintWriter out, BufferedReader in) throws IOException {
        if (parts.length == 3) { // Format: login;username;password
            String username = parts[1];
            String password = parts[2];
            String role = registration.validateUser(username, password);

            if (role != null) {
                out.println("Login successful! Role: " + role);

                if ("STAFF".equalsIgnoreCase(role)) {
                    registration.staffAccess(bookManager, loanManager, username, out, in); // Pass username
                } else if ("PUBLIC".equalsIgnoreCase(role)) {
                    registration.publicAccess(bookManager, loanManager, username, out, in); // Pass username
                }

                return username + " logged out. Enter login details or type 'exit'.";
            } else {
                return "Invalid username or password.";
            }
        } else {
            return "Invalid login command. Usage: login;username;password";
        }
    }
}
