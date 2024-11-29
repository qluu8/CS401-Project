import java.time.LocalDate;

public class Loan {
    private Book book; // Use a Book instance instead of title and author strings
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReserved;

    // Constructor with default loan date as LocalDate.now()
    public Loan(Book book, LocalDate localDate) {
        this("The Great Gatsby", "F. Scott Fitzgerald", LocalDate.now(), java.time.LocalDate.now().plusWeeks(2), book);
    }

    // Constructor with custom loan date
    public Loan(String the_Great_Gatsby, String f_Scott_Fitzgerald, LocalDate loanDate, LocalDate plusWeeks, Book book) {
        this.book = book;
        this.loanDate = loanDate;
        this.dueDate = loanDate.plusWeeks(2); // Default due date is 2 weeks after loan date
        this.returnDate = null;
        this.isReserved = false;
    }

    Loan(Book book) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    // Getters
    public Book getBook() {
        return book;
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

    public boolean isReserved() {
        return isReserved;
    }

    // Extends due date by 2 weeks
    public void extendDueDate() {
        this.dueDate = this.dueDate.plusWeeks(2);
    }

    // Sets the return date to the current date
    public void setReturnDate() {
        this.returnDate = LocalDate.now();
    }

    // Set reserved status
    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
        this.book.setReserved(reserved); // Update the book's reserved status
    }

    // Override toString for detailed loan information
    @Override
    public String toString() {
        return "Title: " + book.getTitle() + ", Author: " + book.getAuthor() +
               ", Loan Date: " + loanDate + ", Due Date: " + dueDate +
               (returnDate != null ? ", Return Date: " + returnDate : ", Not Returned") +
               ", Reserved: " + isReserved;
    }

    AbstractStringBuilder getBorrowerName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    AbstractStringBuilder getBorrowerName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
