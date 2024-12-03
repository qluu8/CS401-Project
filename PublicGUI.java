import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class PublicGUI {
    private JFrame frame;
    private BookManager bookManager;
    private LoanManager loanManager;

    public PublicGUI(String username, BookManager bookManager, LoanManager loanManager) {
        this.bookManager = bookManager;
        this.loanManager = loanManager;
        initializePublicGUI(username);
    }

    private void initializePublicGUI(String username) {
        frame = new JFrame("Library System - Public User");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel lblWelcome = new JLabel("Welcome, " + username + " (Public User)");
        lblWelcome.setBounds(20, 20, 300, 30);
        frame.add(lblWelcome);

        JButton btnViewBooks = new JButton("View Available Books");
        btnViewBooks.setBounds(20, 70, 200, 30);
        frame.add(btnViewBooks);

        JButton btnRequestLoan = new JButton("Request a Loan");
        btnRequestLoan.setBounds(20, 110, 200, 30);
        frame.add(btnRequestLoan);

        JButton btnReturnBook = new JButton("Return a Book");
        btnReturnBook.setBounds(20, 150, 200, 30);
        frame.add(btnReturnBook);

        JButton btnViewLoans = new JButton("View My Loans");
        btnViewLoans.setBounds(20, 190, 200, 30);
        frame.add(btnViewLoans);

        JButton btnRenewBook = new JButton("Renew a Book");
        btnRenewBook.setBounds(20, 230, 200, 30);
        frame.add(btnRenewBook);

        JButton btnReserveBook = new JButton("Reserve a Book");
        btnReserveBook.setBounds(20, 270, 200, 30);
        frame.add(btnReserveBook);

        JButton btnPayFees = new JButton("Pay Late Fees");
        btnPayFees.setBounds(20, 310, 200, 30);
        frame.add(btnPayFees);

        JButton btnExit = new JButton("Exit to Login Screen");
        btnExit.setBounds(20, 350, 200, 30);
        frame.add(btnExit);

        // Action Listeners
        btnViewBooks.addActionListener(e -> {
            StringBuilder availableBooks = new StringBuilder("Available Books:\n\n");
            for (Book book : bookManager.getAllBooks()) {
                if (book.isAvailable()) {
                    availableBooks.append("Book Details:\n")
                            .append("Title: ").append(book.getTitle()).append("\n")
                            .append("Author: ").append(book.getAuthor()).append("\n")
                            .append("Genre: ").append(book.getGenre()).append("\n")
                            .append("ISBN: ").append(book.getISBN()).append("\n")
                            .append("Status: ").append(book.isAvailable() ? "Available" : "Not Available").append("\n")
                            .append("Reserved: ").append(book.isReserved() ? "Yes" : "No").append("\n\n");
                }
            }
            JOptionPane.showMessageDialog(frame, availableBooks.toString());
        });

        btnRequestLoan.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter ISBN of the book to request:");
            Book book = bookManager.searchBookByISBN(isbn);
            if (book != null && book.isAvailable()) {
                loanManager.addLoan(book, username);
                JOptionPane.showMessageDialog(frame, "Loan requested successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Book is unavailable or not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReturnBook.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter ISBN of the book to return:");
            Book book = bookManager.searchBookByISBN(isbn);
            if (book != null && !book.isAvailable()) {
                loanManager.returnLoan(book);
                JOptionPane.showMessageDialog(frame, "Book returned successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Book is not currently loaned or not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnViewLoans.addActionListener(e -> {
            StringBuilder loans = new StringBuilder("Your Loans:\n\n");
            for (Loan loan : loanManager.getLoansByUser(username)) {
                loans.append(loan.toString()).append("\n\n");
            }
            JOptionPane.showMessageDialog(frame, loans.toString());
        });

        btnRenewBook.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter ISBN of the book to renew:");
            if (loanManager.renewLoanForUser(username, isbn)) {
                JOptionPane.showMessageDialog(frame, "Book renewed successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to renew the book. It may not be loaned to you.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReserveBook.addActionListener(e -> {
            String isbn = JOptionPane.showInputDialog("Enter ISBN of the book to reserve:");
            Book book = bookManager.searchBookByISBN(isbn);
            if (book != null && !book.isReserved()) {
                book.setReserved(true);
                JOptionPane.showMessageDialog(frame, "Book reserved successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Book is already reserved or not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnPayFees.addActionListener(e -> {
            StringBuilder overdueLoans = new StringBuilder("Overdue Loans:\n\n");
            for (Loan loan : loanManager.getOverdueLoansForUser(username)) {
                overdueLoans.append(loan.toString()).append("\n\n");
            }

            if (overdueLoans.length() == 0) {
                JOptionPane.showMessageDialog(frame, "You have no overdue loans.");
                return;
            }

            JOptionPane.showMessageDialog(frame, overdueLoans.toString());

            String isbn = JOptionPane.showInputDialog("Enter ISBN of the loan to pay fees for:");
            Loan loan = loanManager.searchLoanByISBN(isbn);

            if (loan != null && loan.getUsername().equals(username)) {
                LocalDate dueDate = loan.getDueDate();
                LocalDate returnDate = LocalDate.now();

                FeeCalculation feeCalc = new FeeCalculation();
                feeCalc.setDueDate(dueDate);
                feeCalc.setReturnDate(returnDate);
                feeCalc.setFlatFee(2.0); // Flat fee
                feeCalc.setDailyFeeRate(0.5); // Daily fee rate

                double totalFee = feeCalc.calculateFees(dueDate, returnDate);
                JOptionPane.showMessageDialog(frame, "Late fee: $" + totalFee);

                int confirm = JOptionPane.showConfirmDialog(frame, "Do you want to pay the fee?", "Confirm Payment", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    loanManager.markFeeAsPaid(loan);
                    JOptionPane.showMessageDialog(frame, "Late fee paid successfully!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid ISBN or no overdue loan found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExit.addActionListener(e -> {
            frame.dispose();
            new LibraryGUI(bookManager, loanManager, new registration()); // Go back to login/registration screen
        });

        frame.setVisible(true);
    }
}
