
	/* Author: Christian Amoranto 11/28/24
 * LoanManager class to handle loan methods
 * Added search and renewal methods
 * UPDATED 11/28/24 Darrell heim added loan/return method
 * UPDATED 12/1/24 (Chandler Hui) Fixed bracket errors and formatting
 * Update: removed package moeikrey (A.S.)
 */

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class LoanManager {
    private List<Loan> activeLoans; // List to manage active loans
    private LoanHistory loanHistory; // History of all loans

    public LoanManager() {
        this.activeLoans = new ArrayList<>();
        this.loanHistory = new LoanHistory();
    }

    // Add a new loan associated with a user
    public void addLoan(Book book, String username) {
        LocalDate currentDate = LocalDate.now();
        if (book.isAvailable()) {
            book.setAvailable(false);
            Loan newLoan = new Loan(book.getISBN(), currentDate, username);
            activeLoans.add(newLoan);
            loanHistory.addLoan(newLoan); // Add to history
            System.out.println("Loan created for book: " + book.getTitle() + " by user: " + username);
        } else {
            System.out.println("Book is not available for loan: " + book.getTitle());
        }
    }

    // Search for a loan by ISBN
    public Loan searchLoanByISBN(String ISBN) {
        for (Loan loan : activeLoans) {
            if (loan.getISBN().equals(ISBN)) {
                return loan;
            }
        }
        return null;
    }

    // Retrieve loans for a specific user
    public List<Loan> getLoansByUser(String username) {
        return loanHistory.getLoansByUser(username);
    }



    // Renew a loan for a specific user
    public boolean renewLoanForUser(String username, String ISBN) {
        for (Loan loan : activeLoans) {
            if (loan.getISBN().equals(ISBN) && loan.getUsername().equals(username)) {
                loan.extendDueDate();
                System.out.println("Loan renewed for book with ISBN: " + ISBN + " by user: " + username);
                return true;
            }
        }
        System.out.println("No active loan found for ISBN: " + ISBN + " associated with user: " + username);
        return false;
    }


    // Return a loan
    public void returnLoan(Book book) {
        Loan loanToReturn = null;

        for (Loan loan : activeLoans) {
            if (loan.getISBN().equals(book.getISBN())) {
                loanToReturn = loan;
                break;
            }
        }

        if (loanToReturn != null) {
            book.setAvailable(true);
            loanToReturn.setReturnDate();
            activeLoans.remove(loanToReturn); // Remove from active loans
            System.out.println("Book returned: " + book.getTitle());
        } else {
            System.out.println("No active loan found for book: " + book.getTitle());
        }
    }

    // View all loans (active and historical)
    public void viewAllLoans() {
        loanHistory.viewLoanHistory();
    }
    
    public LoanHistory getLoanHistory() {
        return loanHistory;
    }
}
