import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class LibraryGUI extends JFrame {
    private ArrayList<User> users = new ArrayList<>(); // List of registered users
    private ArrayList<Book> books = new ArrayList<>(); // List of books
    private ArrayList<Loan> loans = new ArrayList<>(); // List of loans
    private User loggedInUser = null; // Tracks the currently logged-in user

    public LibraryGUI() {
        // Show the login screen on startup
        showLoginScreen();
    }

    // Method to show the login screen
    private void showLoginScreen() {
        JFrame loginFrame = new JFrame("Library Login");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginFrame.add(panel);
        loginFrame.setVisible(true);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (authenticateUser(username, password)) {
                loggedInUser = findUserByUsername(username);
                loginFrame.dispose();
                showMainMenu(); // Show the main library menu
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.");
            }
        });

        // Register button action
        registerButton.addActionListener(e -> {
            loginFrame.dispose();
            showRegistrationScreen();
        });
    }

    // Method to show the registration screen
    private void showRegistrationScreen() {
        JFrame registerFrame = new JFrame("Library Registration");
        registerFrame.setSize(400, 200);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(registerButton);

        registerFrame.add(panel);
        registerFrame.setVisible(true);

        // Register button action
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(registerFrame, "Username and password cannot be empty.");
            } else if (findUserByUsername(username) != null) {
                JOptionPane.showMessageDialog(registerFrame, "Username already exists.");
            } else {
                users.add(new User(username, password));
                JOptionPane.showMessageDialog(registerFrame, "Registration successful! Please log in.");
                registerFrame.dispose();
                showLoginScreen(); // Redirect to login screen
            }
        });
    } 

    // Method to show the main library menu
    private void showMainMenu() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JButton addBookButton = new JButton("Add Book");
        JButton listBooksButton = new JButton("List Books");
        JButton createLoanButton = new JButton("Create Loan");
        JButton returnBookButton = new JButton("Return Book");
        JButton overdueBooksButton = new JButton("Check Overdue Books");

        panel.add(addBookButton);
        panel.add(listBooksButton);
        panel.add(createLoanButton);
        panel.add(returnBookButton);
        panel.add(overdueBooksButton);

        add(panel);

        addBookButton.addActionListener(e -> openAddBookDialog());
        listBooksButton.addActionListener(e -> openListBooksDialog());
        createLoanButton.addActionListener(e -> openCreateLoanDialog());
        returnBookButton.addActionListener(e -> openReturnBookDialog());
        overdueBooksButton.addActionListener(e -> openOverdueBooksDialog());

        setVisible(true);
    }

    // Authenticate user by username and password
    private boolean authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Find user by username
    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Other existing methods for book and loan management
    private void openAddBookDialog() { /* Implementation from your project */ }
    private void openListBooksDialog() { /* Implementation from your project */ }
    private void openCreateLoanDialog() { /* Implementation from your project */ }
    private void openReturnBookDialog() { /* Implementation from your project */ }
    private void openOverdueBooksDialog() { /* Implementation from your project */ }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI();
            gui.setVisible(true);
        });
    }
}
