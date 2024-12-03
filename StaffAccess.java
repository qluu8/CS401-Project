import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class StaffAccess {

    public void handleStaffMenu(BookManager bookManager, LoanManager loanManager, String username, PrintWriter out, BufferedReader in) throws IOException {
        while (true) {
            out.println("\nWelcome, " + username + " (Staff Member)!");
            out.println("1. Add a book");
            out.println("2. Remove a book");
            out.println("3. Edit a book");
            out.println("4. View all books");
            out.println("5. View loan history");
            out.println("6. Exit");
            out.println("Enter your choice:");

            String choice = in.readLine();

            switch (choice) {
                case "1":
                    addBook(bookManager, out, in);
                    break;
                case "2":
                    removeBook(bookManager, out, in);
                    break;
                case "3":
                    editBook(bookManager, out, in);
                    break;
                case "4":
                    viewBooks(bookManager, out);
                    break;
                case "5":
                    viewLoanHistory(loanManager, out);
                    break;
                case "6":
                    out.println("Exiting staff menu.");
                    return;
                default:
                    out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addBook(BookManager bookManager, PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter book title:");
        String title = in.readLine();
        out.println("Enter book author:");
        String author = in.readLine();
        out.println("Enter book genre:");
        String genre = in.readLine();
        out.println("Enter book ISBN:");
        String isbn = in.readLine();

        try {
            bookManager.addBook(title, author, genre, isbn);
            out.println("Book added successfully.");
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }

    private void removeBook(BookManager bookManager, PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter ISBN of the book to remove:");
        String isbn = in.readLine();
        bookManager.removeBook(isbn);
        out.println("Book removed successfully.");
    }

    private void editBook(BookManager bookManager, PrintWriter out, BufferedReader in) throws IOException {
        out.println("Enter ISBN of the book to edit:");
        String isbn = in.readLine();
        Book book = bookManager.searchBookByISBN(isbn);

        if (book == null) {
            out.println("Book not found.");
            return;
        }

        out.println("Editing details for: " + book.getTitle());
        out.println("Enter new title (leave blank to keep current):");
        String newTitle = in.readLine();
        out.println("Enter new author (leave blank to keep current):");
        String newAuthor = in.readLine();
        out.println("Enter new genre (leave blank to keep current):");
        String newGenre = in.readLine();
        out.println("Enter new ISBN (leave blank to keep current):");
        String newISBN = in.readLine();

        // Update book details only if a new value is provided
        if (!newTitle.isBlank()) {
            book.setTitle(newTitle);
        }
        if (!newAuthor.isBlank()) {
            book.setAuthor(newAuthor);
        }
        if (!newGenre.isBlank()) {
            book.setGenre(newGenre);
        }
        if (!newISBN.isBlank()) {
            book.setISBN(newISBN);
        }

        // Save changes to the file
        bookManager.saveBooksToFile();
        out.println("Book details updated successfully.");
    }

    private void viewBooks(BookManager bookManager, PrintWriter out) {
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

    private void viewLoanHistory(LoanManager loanManager, PrintWriter out) {
        // Use LoanManager to access LoanHistory and print details
        List<Loan> loans = loanManager.getLoanHistory().getLoans(); 

        if (loans.isEmpty()) {
            out.println("No loan history available.");
        } else {
            out.println("Loan History:");
            for (Loan loan : loans) {
                out.println(loan); 
            }
        }
    }
}
