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
            List<Book> bookCatalog = loadBooks(); // Load books from catalog
        
            boolean exit = false;
            while (!exit) {
                System.out.println("\nWelcome, Staff Member!");
                System.out.println("1. Loan a book");
                System.out.println("2. Add a book");
                System.out.println("3. Remove a book");
                System.out.println("4. View all books");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
        
                switch (choice) {
                    case 1 -> loanBook(scanner, loanHistory, bookCatalog);
                    case 2 -> addBook(scanner, bookCatalog);
                    case 3 -> removeBook(scanner, bookCatalog);
                    case 4 -> viewBooks(bookCatalog);
                    case 5 -> exit = true;
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            }
            scanner.close();
        }
        
        private static void loanBook(Scanner scanner, LoanHistory loanHistory, List<Book> bookCatalog) {
            System.out.print("Enter ISBN of the book to loan: ");
            String isbn = scanner.nextLine();
        
            Book selectedBook = findBookByISBN(bookCatalog, isbn);
        
            if (selectedBook != null && selectedBook.isAvailable()) {
                Loan loan = new Loan(isbn, java.time.LocalDate.now());
                loanHistory.addLoan(loan);
                selectedBook.setAvailable(false);
        
                System.out.println("Loan created successfully for: " + selectedBook.getTitle() + " by " + selectedBook.getAuthor());
                loanHistory.viewLoanHistory();
            } else {
                System.out.println("Book not found or is currently unavailable.");
            }
        }
        
        private static void addBook(Scanner scanner, List<Book> bookCatalog) {
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();
            System.out.print("Enter book author: ");
            String author = scanner.nextLine();
            System.out.print("Enter book genre: ");
            String genre = scanner.nextLine();
            System.out.print("Enter book ISBN: ");
            String isbn = scanner.nextLine();
        
            Book newBook = new Book(title, author, genre, isbn);
            bookCatalog.add(newBook);
            System.out.println("Book added successfully: " + newBook.getTitle() + " by " + newBook.getAuthor());
        }
        
        private static void removeBook(Scanner scanner, List<Book> bookCatalog) {
            System.out.print("Enter ISBN of the book to remove: ");
            String isbn = scanner.nextLine();
        
            Book bookToRemove = findBookByISBN(bookCatalog, isbn);
            if (bookToRemove != null) {
                bookCatalog.remove(bookToRemove);
                System.out.println("Book removed successfully: " + bookToRemove.getTitle() + " by " + bookToRemove.getAuthor());
            } else {
                System.out.println("Book not found.");
            }
        }
        
        private static void viewBooks(List<Book> bookCatalog) {
            System.out.println("\nBooks in Catalog:");
            for (Book book : bookCatalog) {
                System.out.println(book); // Assuming Book's toString method prints details
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
            Scanner scanner = new Scanner(System.in);
            List<Book> bookCatalog = loadBooks(); // Load books into the catalog
        
            System.out.println("Welcome, Public User!");
            boolean exit = false;
        
            while (!exit) {
                System.out.println("\nChoose an option:");
                System.out.println("1. View available books");
                System.out.println("2. Request a loan");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
        
                switch (choice) {
                    case 1:
                        viewAvailableBooks(bookCatalog);
                        break;
                    case 2:
                        requestLoan(scanner, bookCatalog);
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
            scanner.close();
        }
        
        private static void viewAvailableBooks(List<Book> bookCatalog) {
            System.out.println("Available Books:");
            for (Book book : bookCatalog) {
                if (book.isAvailable()) {
                    System.out.println(book);  // Printing book details using toString() method
                }
            }
        }
        
        private static void requestLoan(Scanner scanner, List<Book> bookCatalog) {
            System.out.print("Enter the ISBN of the book you want to loan: ");
            String isbn = scanner.nextLine();
            Book book = findBookByISBN(bookCatalog, isbn);
        
            if (book != null && book.isAvailable()) {
                // Create the loan using the book's ISBN
                LoanManager loanManager = new LoanManager();
                new Loan(book.getISBN(), java.time.LocalDate.now());
                loanManager.addLoan(book); // Create a loan for the book
                
                System.out.println("Loan requested successfully for: " + book.getTitle());
            } else {
                System.out.println("Book not found or is currently unavailable.");
            }
        }
        
}
