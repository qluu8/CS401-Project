/* Author: Christian Amoranto 11/28/24
 * LoanManager class to handle loan methods
 * Added search and renewal methods
 * UPDATED 11/28/24 Darrell heim added loan/return method
 */

import java.util.ArrayList;
import java.util.List;
import java.util.date;

public class LoanManager {
    private List<Loan> loans; // List to manage active loans

    public LoanManager() {
        this.loans = new ArrayList<>();
    }

    // Add a new loan
    public void addLoan(Book  book ) {
        // logic to create and add a loan for the specified book
        LocalDate currentDate = LocalDate.now();
        if(book.getisAvailable()){
            book.setAvailable(false);
            Loan recent= new Loan(book.getTitle(), book.getauthor(), currentDate);
            this.loans.add(recent);
        }
        else{ 
            out.system.println("book not found");//book not found
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
            return true;
        }
        return false; // Loan not found
    }

    // Return a loan
    public void returnLoan(Book book) {
       for (Loan loan : loans) {
            if (loan.getTitle().equals(book.getTitle())) {
                book.setAvailable(true); 
                FeeCalculation due= new  FeeCalculation;
                due.setDueDate();
                double pay=0;
                pay+=due.calculateFees(loan.getDueDate(),loan.getReturnDate());
                loans.remove(loan); 
                System.out.println(book.getTitle() + " returned.\n");
                System.out.println("must pay "+ pay +" immediately\n");
                return;
    }
}
