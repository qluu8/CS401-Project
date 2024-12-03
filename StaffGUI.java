import javax.swing.*;

public class StaffGUI {
    private JFrame frame;
    private BookManager bookManager;
    private LoanManager loanManager;

    public StaffGUI(String username, BookManager bookManager, LoanManager loanManager) {
        this.bookManager = bookManager;
        this.loanManager = loanManager;
        initializeStaffGUI(username);
    }

    private void initializeStaffGUI(String username) {
        frame = new JFrame("Library System - Staff Member");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome, " + username + " (Staff Member)");
        lblWelcome.setBounds(20, 20, 300, 30);
        frame.add(lblWelcome);

        JButton btnAddBook = new JButton("Add a Book");
        btnAddBook.setBounds(20, 70, 200, 30);
        frame.add(btnAddBook);

        JButton btnRemoveBook = new JButton("Remove a Book");
        btnRemoveBook.setBounds(20, 110, 200, 30);
        frame.add(btnRemoveBook);

        JButton btnEditBook = new JButton("Edit a Book");
        btnEditBook.setBounds(20, 150, 200, 30);
        frame.add(btnEditBook);

        JButton btnViewAllBooks = new JButton("View All Books");
        btnViewAllBooks.setBounds(20, 190, 200, 30);
        frame.add(btnViewAllBooks);

        JButton btnViewLoanHistory = new JButton("View Loan History");
        btnViewLoanHistory.setBounds(20, 230, 200, 30);
        frame.add(btnViewLoanHistory);

        JButton btnExit = new JButton("Exit to Login Screen");
        btnExit.setBounds(20, 270, 200, 30);
        frame.add(btnExit);

        // Action Listeners
        btnAddBook.addActionListener(e -> {
            String title = JOptionPane.showInputDialog("Enter book title:");
            String author = JOptionPane.showInputDialog("Enter book author:");
            String genre = JOptionPane.showInputDialog("Enter book genre:");
            String isbn = JOptionPane.showInputDialog("Enter book ISBN:");

            try {
                bookManager.addBook(title, author, genre, isbn);
                JOptionPane.showMessageDialog(frame, "Book added successfully.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRemoveBook.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter ISBN of the book to remove:");
            if (isbn != null && !isbn.isEmpty()) {
                bookManager.removeBook(isbn);
                JOptionPane.showMessageDialog(frame, "Book removed successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "ISBN cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEditBook.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter ISBN of the book to edit:");
            Book book = bookManager.searchBookByISBN(isbn);
            if (book != null) {
                String newTitle = JOptionPane.showInputDialog("Enter new title (leave blank to keep current):", book.getTitle());
                String newAuthor = JOptionPane.showInputDialog("Enter new author (leave blank to keep current):", book.getAuthor());
                String newGenre = JOptionPane.showInputDialog("Enter new genre (leave blank to keep current):", book.getGenre());
                String newISBN = JOptionPane.showInputDialog("Enter new ISBN (leave blank to keep current):", book.getISBN());

                if (newTitle != null && !newTitle.isBlank()) book.setTitle(newTitle);
                if (newAuthor != null && !newAuthor.isBlank()) book.setAuthor(newAuthor);
                if (newGenre != null && !newGenre.isBlank()) book.setGenre(newGenre);
                if (newISBN != null && !newISBN.isBlank()) book.setISBN(newISBN);

                bookManager.saveBooksToFile();
                JOptionPane.showMessageDialog(frame, "Book details updated successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnViewAllBooks.addActionListener(e -> {
            StringBuilder books = new StringBuilder("All Books:\n\n");
            for (Book book : bookManager.getAllBooks()) {
                books.append("Book Details:\n")
                     .append("Title: ").append(book.getTitle()).append("\n")
                     .append("Author: ").append(book.getAuthor()).append("\n")
                     .append("Genre: ").append(book.getGenre()).append("\n")
                     .append("ISBN: ").append(book.getISBN()).append("\n")
                     .append("Status: ").append(book.isAvailable() ? "Available" : "Not Available").append("\n")
                     .append("Reserved: ").append(book.isReserved() ? "Yes" : "No").append("\n\n");
            }
            JOptionPane.showMessageDialog(frame, books.toString());
        });

        btnViewLoanHistory.addActionListener(e -> {
            StringBuilder history = new StringBuilder("Loan History:\n\n");
            for (Loan loan : loanManager.getLoanHistory().getLoans()) {
                history.append(loan.toString()).append("\n\n");
            }
            JOptionPane.showMessageDialog(frame, history.toString());
        });

        btnExit.addActionListener(e -> {
            frame.dispose();
            new LibraryGUI(bookManager, loanManager, new registration()); // Go back to login/registration screen
        });

        frame.setVisible(true);
    }
}
