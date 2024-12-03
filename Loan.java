/*
 * Author: moeikrey (A.S.) 12/1/24
 * Loan constructor
 */

import java.time.LocalDate;

public class Loan {
    private String ISBN; // Loans are added via ISBN
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String username; // The user who took the loan

    // Constructor
    public Loan(String ISBN, LocalDate loanDate, String username) {
        this.ISBN = ISBN;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusWeeks(2); // Default due date is 2 weeks after loan date
        this.returnDate = null;
        this.username = username;
    }

    // Getters
    public String getISBN() {
        return ISBN;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getUsername() {
        return username;
    }

    // Extend the due date by 2 weeks
    public void extendDueDate() {
        if (returnDate != null) {
            throw new IllegalStateException("Cannot extend due date for a returned loan.");
        }
        this.dueDate = this.dueDate.plusWeeks(2);
    }

    // Mark the book as returned
    public void setReturnDate() {
        if (returnDate != null) {
            throw new IllegalStateException("This loan has already been returned.");
        }
        this.returnDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return "Loan [ISBN=" + ISBN +
               ", Loan Date=" + loanDate +
               ", Due Date=" + dueDate +
               (returnDate != null ? ", Return Date=" + returnDate : ", Not Returned") +
               ", User=" + username + "]";
    }
}

