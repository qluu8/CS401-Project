import javax.swing.*;
import java.awt.*;

public class LibraryGUI {
    private JFrame frame;
    private BookManager bookManager;
    private LoanManager loanManager;
    private registration registration;

    public LibraryGUI(BookManager bookManager, LoanManager loanManager, registration registration) {
        this.bookManager = bookManager;
        this.loanManager = loanManager;
        this.registration = registration;
        createWelcomeScreen();
    }

    private void createWelcomeScreen() {
        frame = new JFrame("Library System - Welcome");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));

        JLabel lblWelcome = new JLabel("Welcome to the Library System!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(lblWelcome);

        JButton btnRegister = new JButton("Register");
        JButton btnLogin = new JButton("Login");

        frame.add(btnRegister);
        frame.add(btnLogin);

        btnRegister.addActionListener(e -> {
            frame.dispose(); // Close the current window
            createRegistrationScreen();
        });

        btnLogin.addActionListener(e -> {
            frame.dispose(); // Close the current window
            createLoginScreen();
        });

        frame.setVisible(true);
    }

    private void createRegistrationScreen() {
        frame = new JFrame("Library System - Register");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblTitle = new JLabel("Register", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(100, 20, 200, 30);
        frame.add(lblTitle);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 80, 100, 30);
        frame.add(lblUsername);

        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(150, 80, 200, 30);
        frame.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 130, 100, 30);
        frame.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 130, 200, 30);
        frame.add(txtPassword);

        JLabel lblRole = new JLabel("Role (PUBLIC/STAFF):");
        lblRole.setBounds(50, 180, 150, 30);
        frame.add(lblRole);

        JTextField txtRole = new JTextField();
        txtRole.setBounds(200, 180, 150, 30);
        frame.add(txtRole);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(150, 240, 100, 30);
        frame.add(btnSubmit);

        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setBounds(50, 300, 300, 30);
        frame.add(lblMessage);

        btnSubmit.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String role = txtRole.getText().trim().toUpperCase();

            if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
                lblMessage.setText("All fields are required!");
                return;
            }

            if (!role.equals("PUBLIC") && !role.equals("STAFF")) {
                lblMessage.setText("Role must be PUBLIC or STAFF!");
                return;
            }

            try {
                boolean isSuccess = registration.registerUser(username, password, role);
                if (isSuccess) {
                    lblMessage.setText("Registration successful! Redirecting to login...");
                    Timer timer = new Timer(2000, evt -> {
                        frame.dispose();
                        createLoginScreen();
                    });
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    lblMessage.setText("User already exists!");
                }
            } catch (Exception ex) {
                lblMessage.setText("Registration failed: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }

    private void createLoginScreen() {
        frame = new JFrame("Library System - Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(50, 50, 100, 30);
        frame.add(lblUsername);

        JTextField txtUsername = new JTextField();
        txtUsername.setBounds(150, 50, 200, 30);
        frame.add(txtUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(50, 100, 100, 30);
        frame.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 100, 200, 30);
        frame.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 150, 100, 30);
        frame.add(btnLogin);

        JLabel lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setBounds(50, 200, 300, 30);
        frame.add(lblMessage);

        btnLogin.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                lblMessage.setText("Username and Password are required!");
                return;
            }

            String role = registration.validateUser(username, password);
            if (role != null) {
                lblMessage.setText("Login successful! Role: " + role);
                Timer timer = new Timer(2000, evt -> {
                    frame.dispose();
                    if ("STAFF".equalsIgnoreCase(role)) {
                        new StaffGUI(username, bookManager, loanManager);
                    } else {
                        new PublicGUI(username, bookManager, loanManager);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                lblMessage.setText("Invalid username or password!");
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        BookManager bookManager = new BookManager();
        LoanManager loanManager = new LoanManager();
        registration registration = new registration();

        new LibraryGUI(bookManager, loanManager, registration);
    }
}
