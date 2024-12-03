/* Author: moeikrey (A.S.)
 * Loan history. Basic implementation 11/24/24
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanHistory {
    private List<Loan> loans;

    public LoanHistory() {
        loans = new ArrayList<>();
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void viewLoanHistory() {
        if (loans.isEmpty()) {
            System.out.println("No loan history available.");
            return;
        }
        System.out.println("Loan History:");
        for (Loan loan : loans) {
            System.out.println(loan);
        }
    }

    // Get all loans
    public List<Loan> getLoans() {
        return new ArrayList<>(loans); // Return a copy to avoid external modifications
    }

    // Get loans by ISBN
    public List<Loan> getLoansByISBN(String ISBN) {
        return loans.stream()
                    .filter(loan -> loan.getISBN().equalsIgnoreCase(ISBN))
                    .collect(Collectors.toList());
    }
    
    public List<Loan> getLoansByUser(String username) {
        return loans.stream()
                    .filter(loan -> loan.getUsername().equals(username))
                    .collect(Collectors.toList());
    }
}
