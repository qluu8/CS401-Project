import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PayingFee_Test
{
	private FeeCalculation testFeeCalc;
	
	void setup()
	{
		testFeeCalc = new FeeCalculation();
		testFeeCalc.setFlatFee(2.0);
		testFeeCalc.setDailyFeeRate(0.5);
	}
	
	@Test
	void setDueDate()
	{
		setup();
		LocalDate due = LocalDate.of(2024, 12, 12);
		testFeeCalc.setDueDate(due);
		
		assertEquals(due, testFeeCalc.getDueDate());
	}
	
	
	@Test
	void TestSetandGetReturnDate()
	{
		setup();
		LocalDate returnDate = LocalDate.of(2024, 12, 12);
		testFeeCalc.setReturnDate(returnDate);
		
		assertEquals(returnDate, testFeeCalc.getReturnDate());
	}
	
	@Test
	void TestSetandGetFlatRate()
	{
		setup();
		testFeeCalc.setFlatFee(5.0);
		assertEquals(5.0, testFeeCalc.getFlatFee());
	}
	
	@Test
	void TestSetandGetDailyFeeRate()
	{
		setup();
		testFeeCalc.setDailyFeeRate(3.0);
		assertEquals(3.0, testFeeCalc.getDailyFeeRate());
	}
	
	@Test
	void TestCalculateFeesNoLate()
	{
		setup();
		
		LocalDate due = LocalDate.of(2024, 12, 3);
		LocalDate returnDate = LocalDate.of(2024, 12, 2);
		
		double total = testFeeCalc.calculateFees(due, returnDate);
		
		assertEquals(0.0, total);
	}

	
	@Test
	void TestCalculateFeeLate()
	{
		setup();
		LocalDate due = LocalDate.of(2024, 12, 10);
		LocalDate returnDate = LocalDate.of(2024, 12, 13);
		
		double total = testFeeCalc.calculateFees(due, returnDate);
		assertEquals(3.5, total);
	}
	
	@Test
	void TestCalculateFeeBeforeDue()
	{
		setup();
		
		LocalDate due = LocalDate.of(2024, 12, 10);
		LocalDate returnDate = LocalDate.of(2024, 12, 7);
		
		double total = testFeeCalc.calculateFees(due, returnDate);
		assertEquals(0.0, total);
	}
	
	
}
