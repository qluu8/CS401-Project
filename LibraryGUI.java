//Author Prayusha Parikh
/*
 * Created this GUI for the First 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class LibraryGUI extends JFrame {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Loan> loans = new ArrayList<>();

    public LibraryGUI() {
        // Set up the main frame
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main menu panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        // Buttons for menu options
        JButton addBookButton = new JButton("Add Book");
        JButton listBooksButton = new JButton("List Books");
        JButton createLoanButton = new JButton("Create Loan");
        JButton returnBookButton = new JButton("Return Book");
        JButton overdueBooksButton = new JButton("Check Overdue Books");

        // Add buttons to the panel
        panel.add(addBookButton);
        panel.add(listBooksButton);
        panel.add(createLoanButton);
        panel.add(returnBookButton);
        panel.add(overdueBooksButton);

        // Add panel to the frame
        add(panel);

        // Button actions
        addBookButton.addActionListener(e -> openAddBookDialog());
        listBooksButton.addActionListener(e -> openListBooksDialog());
        createLoanButton.addActionListener(e -> openCreateLoanDialog());
        returnBookButton.addActionListener(e -> openReturnBookDialog());
        overdueBooksButton.addActionListener(e -> openOverdueBooksDialog());
    }

    // Dialog to add a book
    private void openAddBookDialog() {
        JFrame addBookFrame = new JFrame("Add Book");
        addBookFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel genreLabel = new JLabel("Genre:");
        JTextField genreField = new JTextField();
        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField();

        JButton addButton = new JButton("Add Book");

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(genreLabel);
        panel.add(genreField);
        panel.add(statusLabel);
        panel.add(statusField);
        panel.add(new JLabel());
        panel.add(addButton);

        addBookFrame.add(panel);
        addBookFrame.setVisible(true);

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            String status = statusField.getText();

            books.add(new Book(title, author, genre, status));
            JOptionPane.showMessageDialog(addBookFrame, "Book added successfully!");
            addBookFrame.dispose();
        });
    }

    // Dialog to list all books
    private void openListBooksDialog() {
        JFrame listBooksFrame = new JFrame("List Books");
        listBooksFrame.setSize(400, 300);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        if (books.isEmpty()) {
            textArea.setText("No books available.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Book book : books) {
                sb.append("Title: ").append(book.getTitle())
                  .append(", Author: ").append(book.getAuthor())
                  .append(", Genre: ").append(book.getGenre())
                  .append(", Status: ").append(book.getStatus())
                  .append("\n");
            }
            textArea.setText(sb.toString());
        }

        listBooksFrame.add(new JScrollPane(textArea));
        listBooksFrame.setVisible(true);
    }

    // Dialog to create a loan
    private void openCreateLoanDialog() {
        JFrame createLoanFrame = new JFrame("Create Loan");
        createLoanFrame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel bookTitleLabel = new JLabel("Book Title:");
        JTextField bookTitleField = new JTextField();
        JLabel borrowerLabel = new JLabel("Borrower Name:");
        JTextField borrowerField = new JTextField();

        JButton createButton = new JButton("Create Loan");

        panel.add(bookTitleLabel);
        panel.add(bookTitleField);
        panel.add(borrowerLabel);
        panel.add(borrowerField);
        panel.add(new JLabel());
        panel.add(createButton);

        createLoanFrame.add(panel);
        createLoanFrame.setVisible(true);

        createButton.addActionListener(e -> {
            String bookTitle = bookTitleField.getText();
            String borrowerName = borrowerField.getText();

            Book book = findBookByTitle(bookTitle);
            if (book != null) {
                Loan loan = new Loan(book.getISBN(), LocalDate.now());
                loans.add(loan);
                JOptionPane.showMessageDialog(createLoanFrame, "Loan created successfully!");
                createLoanFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(createLoanFrame, "Book not found!");
            }
        });
    }

    // Dialog to return a book
    private void openReturnBookDialog() {
        JFrame returnBookFrame = new JFrame("Return Book");
        returnBookFrame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JLabel bookTitleLabel = new JLabel("Book Title:");
        JTextField bookTitleField = new JTextField();

        JButton returnButton = new JButton("Return Book");

        panel.add(bookTitleLabel);
        panel.add(bookTitleField);
        panel.add(new JLabel());
        panel.add(returnButton);

        returnBookFrame.add(panel);
        returnBookFrame.setVisible(true);

        returnButton.addActionListener(e -> {
            String bookTitle = bookTitleField.getText();

            Loan loan = findLoanByBookTitle(bookTitle);
            if (loan != null) {
                loan.setReturnDate();
                JOptionPane.showMessageDialog(returnBookFrame, "Book returned successfully!");
                returnBookFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(returnBookFrame, "Loan not found!");
            }
        });
    }

    // Dialog to check overdue books
    private void openOverdueBooksDialog() {
        JFrame overdueBooksFrame = new JFrame("Overdue Books");
        overdueBooksFrame.setSize(400, 300);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
for (Loan loan : loans) {
    if (LocalDate.now().isAfter(loan.getDueDate())) {
        long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
        sb.append("Book: ").append(loan.getBook().getTitle())
          .append(", Borrower: ").append(loan.getBorrowerName())
          .append(", Days Late: ").append(daysLate)
          .append("\n");
    }
}

if (sb.isEmpty()) {
    sb.append("No overdue books.");
}

textArea.setText(sb.toString());

        if (sb.isEmpty()) {
            textArea.setText("No overdue books.");
        } else {
            textArea.setText(sb.toString());
        }

        overdueBooksFrame.add(new JScrollPane(textArea));
        overdueBooksFrame.setVisible(true);
    }

    // Helper methods to find books and loans by title
    private Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    private Loan findLoanByBookTitle(String title) {
        for (Loan loan : loans) {
            if (loan.getBook().getTitle().equalsIgnoreCase(title)) {
                return loan;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI();
            gui.setVisible(true);
        });
    }
}
