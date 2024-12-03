import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class LoanManager_Test
{
	private LoanManager loanManager;
	private Book testBook;
	
	void setup()
	{
		loanManager = new LoanManager();
		testBook = new Book("Title", "Author", "Genre", "123456789", true, false);
	}
	
	@Test
	void TestAddLoan()
	{
		setup();
		
		loanManager.addLoan(testBook, "user");
		
		assertFalse(testBook.isAvailable());
		
		Loan loan = loanManager.searchLoanByISBN("123456789");
		
		assertNotNull(loan);
		assertEquals("user", loan.getUsername());
		assertEquals(LocalDate.now(), loan.getLoanDate());
	}
	
	@Test
	void TestAddLoanBookNotAvailable()
	{
		setup();
		
		testBook.setAvailable(false);
		loanManager.addLoan(testBook, "user");
		Loan loan = loanManager.searchLoanByISBN("123456789");
		
		assertNull(loan);
	}
	
	@Test
	void TestSearchLoanByISBN()
	{
		setup();
		
		loanManager.addLoan(testBook, "user");
		
		Loan loan = loanManager.searchLoanByISBN("123456789");
		
		assertNotNull(loan);
	}

	@Test
	void TestRewnewLoanForUser()
	{
		setup();
		
		loanManager.addLoan(testBook, "user");
		
		boolean testrenew = loanManager.renewLoanForUser("user", "123456789");
		Loan loan = loanManager.searchLoanByISBN("123456789");
		
		assertTrue(testrenew);
		assertEquals(LocalDate.now().plusWeeks(4), loan.getDueDate());
	}
	
	@Test
	void TestRenewLoanForNonUser()
	{
		setup();
		
		boolean testrenew = loanManager.renewLoanForUser("user", "NA");
		
		assertFalse(testrenew);
	}
	
	@Test
	void TestReturnLoan()
	{
		setup();
		
		loanManager.addLoan(testBook, "user");
		loanManager.returnLoan(testBook);
		
		Loan loan = loanManager.getLoanHistory().getLoansByUser("user").get(0);
		
		assertTrue(testBook.isAvailable());
		assertNotNull(loan.getReturnDate());
		assertEquals(LocalDate.now(), loan.getReturnDate());
	}
	
	@Test
	void TestNoReturnLoan()
	{
		setup();
		
		loanManager.returnLoan(testBook);
		
		assertTrue(testBook.isAvailable());
	}
	
	@Test
	void TestGetLoansByUser()
	{
		setup();
		
		loanManager.addLoan(testBook, "user");
		List<Loan> loans = loanManager.getLoansByUser("user");
		
		assertEquals(1, loans.size());
		assertEquals("user", loans.get(0).getUsername());
	}
	
	@Test
	void TestViewAllLoans()
	{
		setup();
		
		loanManager.addLoan(testBook, "user");
		loanManager.viewAllLoans();
		
		List<Loan> loans = loanManager.getLoanHistory().getLoansByUser("user");
		assertEquals(1, loans.size());
	}
}
