/* Author: Christian Amoranto 11/28/24
 * LoanManager class to handle loan methods
 * Added search and renewal methods
 * Still needs check out and return loan methods
 */

import java.util.ArrayList;
import java.util.List;

public class LoanManager {
    private List<Loan> loans; // List to manage active loans

    public LoanManager() {
        this.loans = new ArrayList<>();
    }

    // Add a new loan
    public Loan addLoan {
        // logic to create and add a loan for the specified book

        return null; 
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
            return true;
        }
        return false; // Loan not found
    }

    // Return a loan
    public Loan returnLoan {
        // logic to return a loan
  
        return null; // Placeholder return
    }
}
