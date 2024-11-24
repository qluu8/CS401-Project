/* Author: moeikrey (A.S.)
 * Loan history. Basic implementation 11/24/24
 */
import java.util.ArrayList;
import java.util.List;

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
}