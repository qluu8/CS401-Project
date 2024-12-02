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
    private static final String USERS_FILE = "users.txt";

    public ClientHandler(Socket socket, BookManager bookManager) {
        this.clientSocket = socket;
        this.bookManager = bookManager;
        this.loanManager = loanManager;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String request;

            while ((request = in.readLine()) != null) {
                String[] parts = request.split(";");
                String command = parts[0];

                switch (command.toLowerCase()) {
                    case "login":
                    if (parts.length == 3) { // Format: login;username;password
                        String username = parts[1];
                        String password = parts[2];
                        String role = parts[3];
                        if (role != null) {
                            out.println("Login successful! Role: " + role);
                        } else {
                            out.println("Invalid username or password!");
                        }
                    } else {
                        out.println("Invalid login command. Usage: login;username;password");
                    }
                        break;

                    case "addbook":
                        if (parts.length == 5) { // Format: addbook;title;author;genre;ISBN
                            String title = parts[1];
                            String author = parts[2];
                            String genre = parts[3];
                            String ISBN = parts[4];
                            bookManager.addBook(title, author, genre, ISBN);
                            out.println("Book added successfully.");
                        } else {
                            out.println("Invalid addbook command. Usage: addbook;title;author;genre;ISBN");
                        }
                        break;

                    case "removebook":
                        if (parts.length == 2) { // Format: removebook;ISBN
                            String ISBN = parts[1];
                            bookManager.removeBook(ISBN);
                            out.println("Book removed successfully.");
                        } else {
                            out.println("Invalid removebook command. Usage: removebook;ISBN");
                        }
                        break;

                    case "reserve":
                        if (parts.length == 2) { // Format: reserve;ISBN
                            String ISBN = parts[1];
                            Book bookToReserve = bookManager.searchBookByISBN(ISBN);
                            if (bookToReserve != null && bookToReserve.isAvailable() && !bookToReserve.isReserved()) {
                                bookToReserve.setReserved(true);
                                bookToReserve.setAvailable(false); // Mark as not available after reservation
                                out.println("Book reserved successfully.");
                            } else {
                                out.println("Book could not be reserved. It may not be available or already reserved.");
                            }
                        } else {
                            out.println("Invalid reserve command. Usage: reserve;ISBN");
                        }
                        break;

                   case "renew": // Format: renew;ISBN
                        if (parts.length == 2) {
                            String ISBN = parts[1];
                            Loan loanToRenew = loanManager.searchLoanByISBN(ISBN);
                            if (loanToRenew != null) {
                                loanToRenew.extendDueDate();
                                out.println("Book renewed successfully. New Due Date: " + loanToRenew.getDueDate());
                            } else {
                                out.println("Book could not be renewed. No active loan found for the given ISBN.");
                            }
                        } else {
                            out.println("Invalid renew command. Usage: renew;ISBN");
                        }
                        break;

                    case "search":
                        if (parts.length == 2) { // Format: search;title
                            String title = parts[1];
                            Book foundBook = bookManager.searchBook(title);
                            if (foundBook != null) {
                                out.println("Book found: " + foundBook);
                            } else {
                                out.println("Book not found.");
                            }
                        } else {
                            out.println("Invalid search command. Usage: search;title");
                        }
                        break;

                    case "exit":
                        out.println("Closing connection.");
                        return;

                    default:
                        out.println("Unknown command. Please try again.");
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
}
