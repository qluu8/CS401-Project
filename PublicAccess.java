import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class PublicAccess {

    public void handlePublicMenu(BookManager bookManager, LoanManager loanManager, String username, PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            out.println("\nWelcome, Public User!");
            out.println("1. View available books");
            out.println("2. Request a loan");
            out.println("3. Return a book");
            out.println("4. View my loans");
            out.println("5. Renew a book");
            out.println("6. Reserve a book");
            out.println("7. Exit");
            out.println("Enter your choice:");

            String choice = in.readLine();

            switch (choice) {
                case "1":
                    viewAvailableBooks(bookManager, out);
                    break;
                case "2":
                    requestLoan(loanManager, bookManager, username, out, in);
                    break;
                case "3":
                    returnBook(loanManager, bookManager, out, in);
                    break;
                case "4":
                    viewUserLoans(loanManager, username, out);
                    break;
                case "5":
                    renewBook(loanManager, username, out, in);
                    break;
                case "6":
                    reserveBook(bookManager, out, in);
                    break;
                case "7":
                    out.println("Exiting public menu.");
                    return;
                default:
                    out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void viewAvailableBooks(BookManager bookManager, PrintWriter out) {
        out.println("Book Catalog");
        out.println("____________");
        out.println(); 

        for (Book book : bookManager.getAllBooks()) {
            out.println("Book Details:");
            out.println("Title: " + book.getTitle());
            out.println("Author: " + book.getAuthor());
            out.println("Genre: " + book.getGenre());
            out.println("ISBN: " + book.getISBN());
            out.println("Status: " + (book.isAvailable() ? "Available" : "Not Available"));
            out.println("Reserved: " + (book.isReserved() ? "Yes" : "No"));
            out.println(); 
        }
    }

    private void requestLoan(LoanManager loanManager, BookManager bookManager, String username, PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter ISBN of the book to request:");
        String isbn = in.readLine();
        Book book = bookManager.searchBookByISBN(isbn);

        if (book != null && book.isAvailable()) {
            loanManager.addLoan(book, username);
            out.println("Loan requested successfully for: " + book.getTitle());
        } else {
            out.println("Book not found or is currently unavailable.");
        }
    }

    private void returnBook(LoanManager loanManager, BookManager bookManager, PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter ISBN of the book to return:");
        String isbn = in.readLine();
        Book book = bookManager.searchBookByISBN(isbn);

        if (book != null && !book.isAvailable()) {
            loanManager.returnLoan(book);
            out.println("Book returned successfully: " + book.getTitle());
        } else {
            out.println("Book not found or is not currently loaned out.");
        }
    }

    private void viewUserLoans(LoanManager loanManager, String username, PrintWriter out) {
        List<Loan> userLoans = loanManager.getLoansByUser(username);

        if (userLoans.isEmpty()) {
            out.println("You have no active loans.");
        } else {
            out.println("Your Active Loans:");
            for (Loan loan : userLoans) {
                out.println(loan); 
            }
        }
    }

    private void renewBook(LoanManager loanManager, String username, PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter the ISBN of the book to renew:");
        String isbn = in.readLine();

        boolean success = loanManager.renewLoanForUser(username, isbn);

        if (success) {
            out.println("Book renewed successfully!");
        } else {
            out.println("Failed to renew book. It may not be loaned to you or already returned.");
        }
    }

    private void reserveBook(BookManager bookManager, PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter the ISBN of the book to reserve:");
        String isbn = in.readLine();
        Book book = bookManager.searchBookByISBN(isbn);

        if (book != null && !book.isReserved()) {
            book.setReserved(true);
            out.println("Book reserved successfully: " + book.getTitle());
        } else if (book != null && book.isReserved()) {
            out.println("Book is already reserved.");
        } else {
            out.println("Book not found or is currently unavailable.");
        }
    }
}
