//Author: Prayusha Parikh
/*
 * I have created this main.java to connect all the Classes together.
 */


import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create a Book instance
        Book book = new Book("Harry Potter", "J.K. Rowling", "Fantasy", "Available");

        // Step 2: Create a Loan for the Book
        Loan loan = new Loan(book);
        System.out.println("Loan created:\n" + loan);

        // Step 3: Extend the due date by 2 weeks
        System.out.println("\nExtending the due date...");
        loan.extendDueDate();
        System.out.println("Updated Loan:\n" + loan);

        // Step 4: Reserve the Book
        System.out.println("\nReserving the book...");
        loan.setReserved(true);
        System.out.println("Loan after reserving the book:\n" + loan);

        // Step 5: Return the Book
        System.out.println("\nReturning the book...");
        loan.setReturnDate();
        System.out.println("Loan after returning the book:\n" + loan);

        // Step 6: Create another loan with a custom loan date
        System.out.println("\nCreating another loan with a custom loan date...");
        LocalDate customLoanDate = LocalDate.of(2024, 10, 15); // Custom loan date
        Loan customLoan = new Loan("The Great Gatsby", "F. Scott Fitzgerald", customLoanDate, java.time.LocalDate.now().plusWeeks(2), book);
        System.out.println("Custom Loan:\n" + customLoan);

        // Step 7: Check overdue logic
        System.out.println("\nChecking overdue logic...");
        LocalDate currentDate = LocalDate.of(2024, 11, 28); // Simulated current date
        if (currentDate.isAfter(customLoan.getDueDate())) {
            long daysLate = customLoan.getDueDate().until(currentDate).getDays();
            double dailyFeeRate = 0.5; // $0.5 per day late
            double totalFee = daysLate * dailyFeeRate;
            System.out.println("The book is overdue by " + daysLate + " days.");
            System.out.println("Total late fee: $" + totalFee);
        } else {
            System.out.println("The book is not overdue.");
        }
    }
}
