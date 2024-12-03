import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class LibraryGUI2 {
    private BookManager bookManager;
    private LoanManager loanManager;
    private PublicAccess publicAccess;
    private JTextArea textArea;
    private static final String USER_FILE = "users.txt";
    private Map<String, String[]> users = new HashMap<>(); // username -> [password, role]
    private boolean loggedIn = false;
    private String currentUserRole = "";
    private JPanel bottomPanel;
    private JFrame frame;

    public LibraryGUI2() {
        bookManager = new BookManager();
        loanManager = new LoanManager();
        publicAccess = new PublicAccess(loanManager, bookManager);
        loadUsers();

        frame = new JFrame("Library System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Text Area for Display
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Display the welcome message
        textArea.setText("Welcome to the Library System!\n\n");
        textArea.append("Please login or register to access the system.\n");

        // Bottom Panel for Buttons
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        // Login/Register Buttons
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            if (!loggedIn) showLoginDialog();
        });

        registerButton.addActionListener(e -> {
            if (!loggedIn) showRegisterDialog();
        });

        bottomPanel.add(loginButton);
        bottomPanel.add(registerButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Load Users from File
    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.put(parts[0], new String[]{parts[1], parts[2]});
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user data from file: " + e.getMessage());
        }
    }

    // Save Users to File
    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (Map.Entry<String, String[]> entry : users.entrySet()) {
                String username = entry.getKey();
                String[] data = entry.getValue();
                bw.write(username + "," + data[0] + "," + data[1]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving user data to file: " + e.getMessage());
        }
    }

    // Login Dialog
    private void showLoginDialog() {
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        Object[] loginFields = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, loginFields, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (users.containsKey(username) && users.get(username)[0].equals(password)) {
                loggedIn = true;
                currentUserRole = users.get(username)[1];
                JOptionPane.showMessageDialog(null, "Login successful! Welcome " + username + " (" + currentUserRole + ")");
                showLibraryMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid login credentials.");
            }
        }
    }

    // Registration Dialog
    private void showRegisterDialog() {
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField roleField = new JTextField(20);

        Object[] registerFields = {
            "Username:", usernameField,
            "Password:", passwordField,
            "Role (PUBLIC/STAFF):", roleField
        };

        int option = JOptionPane.showConfirmDialog(null, registerFields, "Register", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText().toUpperCase();

            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists.");
                return;
            }

            if (!role.equals("PUBLIC") && !role.equals("STAFF")) {
                JOptionPane.showMessageDialog(null, "Invalid role. Please enter either PUBLIC or STAFF.");
                return;
            }

            users.put(username, new String[]{password, role});
            saveUsers();
            JOptionPane.showMessageDialog(null, "Registration successful! Welcome " + username + " (" + role + ")");
        }
    }

    // Library Menu
    private void showLibraryMenu() {
        textArea.setText("");
        bottomPanel.removeAll();

        if (currentUserRole.equals("STAFF")) {
            JButton addBookButton = new JButton("Add Book");
            JButton removeBookButton = new JButton("Remove Book");
            JButton viewBooksButton = new JButton("View Books");
            JButton manageLoansButton = new JButton("Manage Loans");

            addBookButton.addActionListener(e -> addBook());
            removeBookButton.addActionListener(e -> removeBook());
            viewBooksButton.addActionListener(e -> viewBooks());
            manageLoansButton.addActionListener(e -> manageLoans());

            bottomPanel.add(addBookButton);
            bottomPanel.add(removeBookButton);
            bottomPanel.add(viewBooksButton);
            bottomPanel.add(manageLoansButton);
        } else if (currentUserRole.equals("PUBLIC")) {
            JButton viewBooksButton = new JButton("View Books");
            JButton borrowBookButton = new JButton("Borrow Book");
            JButton payFeesButton = new JButton("Pay Overdue Fees");

            viewBooksButton.addActionListener(e -> viewBooks());
            borrowBookButton.addActionListener(e -> borrowBook());
            payFeesButton.addActionListener(e -> payOverdueFees());

            bottomPanel.add(viewBooksButton);
            bottomPanel.add(borrowBookButton);
            bottomPanel.add(payFeesButton);
        }

        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    // Manage Loans
    private void manageLoans() {
        textArea.setText("Loan Management:\n");
        for (Loan loan : loanManager.getAllLoans()) {
            textArea.append(loan.toString() + "\n");
        }

        String loanID = JOptionPane.showInputDialog("Enter the ID of the loan to mark as returned:");
        if (loanID != null && !loanID.trim().isEmpty()) {
            try {
                loanManager.returnLoan(loanID);
                JOptionPane.showMessageDialog(null, "Loan returned successfully!");
                manageLoans();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    // Add Book
    private void addBook() {
        String title = JOptionPane.showInputDialog("Enter book title:");
        String author = JOptionPane.showInputDialog("Enter book author:");
        String genre = JOptionPane.showInputDialog("Enter book genre:");
        String isbn = JOptionPane.showInputDialog("Enter book ISBN:");
        bookManager.addBook(title, author, genre, isbn);
        JOptionPane.showMessageDialog(null, "Book added successfully!");
    }

    // Remove Book
    private void removeBook() {
        String isbn = JOptionPane.showInputDialog("Enter the ISBN of the book to remove:");
        bookManager.removeBook(isbn);
        JOptionPane.showMessageDialog(null, "Book removed successfully!");
    }

    // Borrow Book
    private void borrowBook() {
        String isbn = JOptionPane.showInputDialog("Enter the ISBN of the book to borrow:");
        publicAccess.borrowBook(isbn);
        JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
    }

    // Pay Overdue Fees
    private void payOverdueFees() {
        String isbn = JOptionPane.showInputDialog("Enter the ISBN of the book to pay fees for:");
        double amount = publicAccess.payOverdueFees(isbn);
        JOptionPane.showMessageDialog(null, "Paid $" + amount + " for overdue fees.");
    }

    // View Books
    private void viewBooks() {
        textArea.setText("");
        for (Book book : bookManager.getAllBooks()) {
            textArea.append(book.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        new LibraryGUI2();
    }
}
