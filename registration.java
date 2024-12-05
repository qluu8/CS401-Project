/* Author: Tendou-0 (Chandler Hui)
 * User Registration Basic implementation 11/24/24
 * 12/1/24 Update (Chandler Hui) Updated staffAccess to search by ISBN
 * Also updated publicAccess to view and request loans
 * 12/1/24 Update (Chandler Hui) Added function to add/remove booksfor staffAccess
 */
import java.io.*;
import java.util.*;

public class registration {
    private static final String USERS_FILE = "users.txt";
    private StaffAccess staffAccess;
    private PublicAccess publicAccess;

    public registration() {
        this.staffAccess = new StaffAccess();
        this.publicAccess = new PublicAccess();
    }

    public void staffAccess(BookManager bookManager, LoanManager loanManager, String username, PrintWriter out, BufferedReader in) throws IOException {
        staffAccess.handleStaffMenu(bookManager, loanManager, username, out, in);
    }

    public void publicAccess(BookManager bookManager, LoanManager loanManager, String username, PrintWriter out, BufferedReader in) throws IOException {
        publicAccess.handlePublicMenu(bookManager, loanManager, username, out, in);
    }

 // Register a new user
    public String registerUser(PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter username: ");
        String username = in.readLine();
        out.println("Enter password: ");
        String password = in.readLine();
        out.println("Enter role (PUBLIC/STAFF): ");
        String role = in.readLine().toUpperCase();

        if (!role.equals("PUBLIC") && !role.equals("STAFF")) {
            return "Invalid role! Only PUBLIC or STAFF are allowed.";
        }

        // Check if user already exists
        if (isUserExists(username)) {
            return "User already exists!";
        }

        // Register new user
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.newLine(); 
            writer.write(username + "," + password + "," + role);
        }

        // Provide login instructions after registration
        return "User registered successfully!\n"
                + "Please enter your login info:\n"
                + "Example: login;" + username + ";" + password;
    }
    
  //Overloaded method for GUI-based registration
    public boolean registerUser(String username, String password, String role) throws IOException {
        if (isUserExists(username)) {
            return false; // User already exists
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.newLine();
            writer.write(username + "," + password + "," + role);   
        }
        return true;
    }

    // Validate user credentials
    public String validateUser(String username, String password) {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return null; // Return null if file is missing
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    return parts[2]; // Return role if valid
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Invalid credentials
    }

    private boolean isUserExists(String username) {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return false; // No users registered yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

