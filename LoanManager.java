
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
import java.util.*;

public class LoanManager {
    private List<Loan> loans; // List to manage active loans

    public LoanManager() {
        this.loans = new ArrayList<>();
    }

    // Add a new loan
    public void addLoan(Book  book ) {
        // logic to create and add a loan for the specified book
        LocalDate currentDate = LocalDate.now();
        if (book.isAvailable()) { // Check if the book is available
            book.setAvailable(false); // Mark the book as unavailable
            Loan newLoan = new Loan(book.getTitle(),book.getISBN(), currentDate); // Create a new loan using the book's ISBN
            this.loans.add(newLoan); // Add the loan to the list
            System.out.println("Loan created for book: " + book.getTitle());
        } else {
            System.out.println("Book is not available for loan: " + book.getTitle());
        }
    }

    // Search for a loan by ISBN
    public Loan searchLoanByISBN(String ISBN) {
        for (Loan loan : loans) {
            if (loan.getISBN().equals(ISBN)) {
                return loan; // Return the loan if found
            }
        }
        return null; // Loan not found
    }

   // Renew a loan
    public boolean renewLoan(String ISBN) {
        Loan loan = searchLoanByISBN(ISBN);

        if (loan != null) {
            loan.extendDueDate(); // Extend the due date by 2 weeks
            System.out.println("Loan renewed for book with ISBN: " + ISBN);
            return true;
        }

        System.out.println("No active loan found for ISBN: " + ISBN);
        return false;
    }
    
    // Return a loan
    public void returnLoan(Book book) {
       for (Loan loan : loans) {
            if (loan.getISBN().equals(book.getISBN())) {
                book.setAvailable(true); 
                FeeCalculation due = new  FeeCalculation();
                double pay=0;
                pay+=due.calculateFees(loan.getDueDate(),loan.getReturnDate());
                loans.remove(loan); 
                System.out.println(book.getTitle() + " returned.\n");
                System.out.println("must pay "+ pay +" immediately\n");
                return;
            }
    }
}
}
