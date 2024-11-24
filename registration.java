/* Author: tendou_. (C.H.)
 * User Registration Basic implementation 11/24/24
 */
import java.io.*;
import java.util.*;

public class registration {
    private static final String USERS_FILE = "users.txt";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Library System");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1 -> registerUser(scanner);
            case 2 -> loginUser(scanner);
            default -> System.out.println("Invalid choice!");
        }

        scanner.close();
    }

    private static void registerUser(Scanner scanner) throws IOException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (PUBLIC/STAFF): ");
        String role = scanner.nextLine().toUpperCase();

        if (!role.equals("PUBLIC") && !role.equals("STAFF")) {
            System.out.println("Invalid role! Only PUBLIC or STAFF are allowed.");
            return;
        }

        // Check if user already exists
        if (isUserExists(username)) {
            System.out.println("User already exists!");
            return;
        }

        // Register new user
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(username + "," + password + "," + role);
            writer.newLine();
        }
        System.out.println("User registered successfully!");
    }

    private static void loginUser(Scanner scanner) throws IOException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Validates user
        String role = validateUser(username, password);
        if (role == null) {
            System.out.println("Invalid username or password!");
            return;
        }

        System.out.println("Login successful! Role: " + role);
        if (role.equals("STAFF")) {
            staffAccess();
        } else if (role.equals("PUBLIC")) {
            publicAccess();
        }
    }

    private static boolean isUserExists(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

        // Verification
    private static String validateUser(String username, String password) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    return parts[2];
                }
            }
        }
        return null;
    }

        // Message for staff users, also so I can get feedback this thing actually works
    private static void staffAccess() {
        LoanHistory loanHistory = new LoanHistory();
        System.out.println("Welcome, Staff Member!");

        // Example functionality for staff members, currently book info is static, needs to be interchangable later
        Loan loan1 = new Loan("The Great Gatsby", "F. Scott Fitzgerald", 
                              java.time.LocalDate.now(), 
                              java.time.LocalDate.now().plusWeeks(2), null);
        loanHistory.addLoan(loan1);
        loanHistory.viewLoanHistory();
    }

        // Message for public users
    private static void publicAccess() {
        System.out.println("Welcome, Public User!");
        System.out.println("You can view books or request loans.");
    }
}
