import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Book_Test 
{

	@Test
	void TestTitle() 
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		
		assertEquals("Test Title", test.getTitle());
	}
	
	@Test
	void TestAuthor()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		
		assertEquals("Test Author", test.getAuthor());
	}
	
	@Test
	void TestGenre()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		
		assertEquals("Test Genre", test.getGenre());
	}

	@Test
	void TestISBN()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		
		assertEquals("Test ISBN", test.getISBN());
	}
	
	@Test
	void TestSetTitle()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		test.setTitle("The Hobbit");
		assertEquals("The Hobbit", test.getTitle());
	}
	
	@Test
	void TestSetAuthor()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		test.setAuthor("Author");
		assertEquals("Author", test.getAuthor());
	}
	
	@Test
	void TestSetGenre()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		test.setGenre("Fantasy");
		assertEquals("Fantasy", test.getGenre());
	}
	
	@Test
	void TestSetISBN()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		test.setISBN("123456789");
		assertEquals("123456789", test.getISBN());
	}
	
	@Test
	void isAvailable()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		assertTrue(test.isAvailable());
	}
	
	@Test
	void isReserved()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		assertFalse(test.isReserved());
	}
	
	@Test
	void TestSetAvailable()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		test.setAvailable(false);
		assertFalse(test.isAvailable());
		
		test.setAvailable(true);
		assertTrue(test.isAvailable());
	}
	
	@Test
	void TestSetReserved()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		test.setReserved(true);
		assertTrue(test.isReserved());
		
		test.setReserved(false);
		assertFalse(test.isReserved());		
	}
	
	@Test
	void TesttoString()
	{
		Book test = new Book("Test Title", "Test Author", "Test Genre", "Test ISBN");
		String expected = "Book Details:\n" +
                "Title: " + test.getTitle() + "\n" +
                "Author: " + test.getAuthor() + "\n" +
                "Genre: " + test.getGenre() + "\n" +
                "ISBN: " + test.getISBN() + "\n" +
                "Status: " + (test.isAvailable() ? "Available" : "Not Available") + "\n" +
                "Reserved: " + (test.isReserved() ? "Yes" : "No");
		
		assertEquals(expected, test.toString());
	}
}
