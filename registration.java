/* Author: tendou_. (Chandler Hui)
 * User Registration Basic implementation 11/24/24
 * 12/1/24 Update (Chandler Hui) Updated staffAccess to search by ISBN
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
            Scanner scanner = new Scanner(System.in);
            LoanHistory loanHistory = new LoanHistory();
            
            // Load books from the catalog
            List<Book> bookCatalog = loadBooks();
        
            System.out.println("Welcome, Staff Member!");
            System.out.print("Enter ISBN of the book to loan: ");
            String isbn = scanner.nextLine(); // Take input for ISBN
        
            // Find the book by ISBN using the method you provided
            Book selectedBook = findBookByISBN(bookCatalog, isbn);
        
            if (selectedBook != null && selectedBook.isAvailable()) {
                // Create the loan using the ISBN (not the Book object directly)
                Loan loan = new Loan(isbn, java.time.LocalDate.now());
                loanHistory.addLoan(loan);
        
                // Mark the book as unavailable
                selectedBook.setAvailable(false);
        
                System.out.println("Loan created successfully!");
                loanHistory.viewLoanHistory();
            } else {
                System.out.println("Book not found or is currently unavailable.");
            }
        }
        
        

private static Book findBookByISBN(List<Book> bookCatalog, String isbn) {
    for (Book book : bookCatalog) {
        if (book.getISBN().equals(isbn)) {
            return book;
        }
    }
    return null;
}

private static List<Book> loadBooks() {
    // Placeholder: Load books from a file or create sample books for testing
    List<Book> books = new ArrayList<>();
    books.add(new Book("1984", "George Orwell", "Dystopian", "1234567890"));
    books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", "0987654321"));
    return books;
}

        

        // Message for public users
    private static void publicAccess() {
        System.out.println("Welcome, Public User!");
        System.out.println("You can view books or request loans.");

        //ADD BRANCH TO FRONT END FOR PUBLIC USER A.S.
        //CREATE CLASSES FOR THEM BOTH POSSIBLY
    }
}
