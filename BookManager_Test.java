import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookManager_Test 
{
	private BookManager collection;
	
	void makeCollection()
	{
		collection = new BookManager();
	}
	
	
	@Test
	void TestAddBook() 
	{
		makeCollection();
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		
		assertEquals(1, collection.getBookCount());
		assertEquals("Harry Potter", collection.searchBook("Harry Potter").getTitle());
		assertEquals("J.K Rowling", collection.searchBook("Harry Potter").getAuthor());
		assertEquals("Fantasy", collection.searchBook("Harry Potter").getGenre());
		assertEquals("123456789", collection.searchBook("Harry Potter").getISBN());
	}

	@Test
	void TestRemoveBook()
	{
		makeCollection();
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		
		assertEquals(1, collection.getBookCount());
		collection.removeBook("123456789");
		assertEquals(0, collection.getBookCount());
		assertNull(collection.searchBook("Harry Potter"));
	}
	
	@Test
	void TestSearchBook()
	{
		makeCollection();
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		collection.addBook("The Hobbit", "J.R.R. Tolkien", "Fantasy", "987654321");
		
		assertNotNull(collection.searchBook("Harry Potter"));
		assertEquals("Harry Potter", collection.searchBook("Harry Potter").getTitle());
		
		assertNotNull(collection.searchBook("The Hobbit"));
		assertEquals("The Hobbit", collection.searchBook("The Hobbit").getTitle());
		
		assertNull(collection.searchBook("N/A"));
	}
	
	@Test
	void TestSearchByISBN()
	{
		makeCollection();
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		collection.addBook("The Hobbit", "J.R.R. Tolkien", "Fantasy", "987654321");
		
		assertNotNull(collection.searchBookByISBN("123456789"));
		assertEquals("Harry Potter", collection.searchBookByISBN("123456789").getTitle());
		
		assertNotNull(collection.searchBookByISBN("987654321"));
		assertEquals("The Hobbit", collection.searchBookByISBN("987654321").getTitle());
	}
	
	@Test
	void BookCount()
	{
		makeCollection();
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		collection.addBook("The Hobbit", "J.R.R. Tolkien", "Fantasy", "987654321");
		
		assertEquals(2, collection.getBookCount());
	}
	
	@Test
	void TestDuplicatISBN()
	{
		makeCollection();
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		
		Exception exception = assertThrows(IllegalArgumentException.class, () -> 
		{
            collection.addBook("Duplicate Book", "Author", "Genre", "123456789");
        });

        assertEquals("Book with the given ISBN already exists.", exception.getMessage());
        assertEquals(1, collection.getBookCount());
	}
	
	@Test
	void TestRemoveNonExistentBook()
	{
		makeCollection();
		
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		collection.removeBook("987654321");
		assertEquals(1, collection.getBookCount());
	}
	
	@Test
	void TestGetAllBooks()
	{
		makeCollection();
		collection.addBook("Harry Potter", "J.K Rowling", "Fantasy", "123456789");
		collection.addBook("The Hobbit", "J.R.R. Tolkien", "Fantasy", "987654321");
		
		assertEquals(2, collection.getBookCount());
		assertTrue(collection.getAllBooks().stream().anyMatch(book -> book.getTitle().equals("Harry Potter")));
        assertTrue(collection.getAllBooks().stream().anyMatch(book -> book.getTitle().equals("The Hobbit")));
	}
	
	@Test
	void TestEmptyBooks()
	{
		makeCollection();
		assertEquals(0, collection.getBookCount());
		assertNull(collection.searchBook("N/A"));
		assertNull(collection.searchBookByISBN("123456789"));
	}
}
