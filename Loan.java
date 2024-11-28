/* Author: moeikrey (A.S.)
 * Loan constructor. Basic implementation 11/24/24
 * Please go over and update as needed. I am waiting for other collaborations and I will redefine a loan once other 
 * modules are completed. Uses date off of time library
 */
import java.time.LocalDate;

public class Loan {
    private String bookTitle;
    private String author;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
        private boolean isReserved;
    
        public Loan(String bookTitle, String author, LocalDate loanDate) {
            this.bookTitle = bookTitle;
            this.author = author;
            this.loanDate = loanDate;
            this.dueDate = loanDate.plusWeeks(2);
            this.returnDate = null;
        }
    
        public String getBookTitle() {
            return bookTitle;
        }
    
        public String getAuthor() {
            return author;
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
    
        // Extends due date by 2 weeks
        public void extendDueDate() {
            this.dueDate = this.dueDate.plusWeeks(2);
        }
    
        public boolean isReserved() {
            return isReserved;
        }
    
        public void setReserved(boolean reserved) {
            this.isReserved = reserved;
        if (reserved) {
            book.setReserved(true); // Update the book's reserved status
        } else {
            book.setReserved(false); // Clear the book's reserved status
        }
    }
    //set return date only to be called when loan returned
        public void setReturnDate() {
            LocalDate currentDate = LocalDate.now();
            this.returnDate=currentDate;
        }
    }

    @Override
    public String toString() {
        return "Title: " + bookTitle + ", Author: " + author + 
               ", Loan Date: " + loanDate + ", Due Date: " + dueDate + 
               (returnDate != null ? ", Return Date: " + returnDate : ", Not Returned");
        // This will print title, author, loan date, due date, and if there is a return date past it will show that it hasn't been returned.
    }

    private static class book {

        private static void setReserved(boolean b) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public book() {
        }
    }
}
