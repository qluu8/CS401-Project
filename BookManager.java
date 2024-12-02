/*
 * Author: Andreas Sotiras 11/27/24
 * I created a basic template with a bookmanager class. Please look over this @Christian
 * And add based off what seems to be needed.
 * 
 * Edit: Quan Luu 11/27/2024
 * Added new variable bookCount to keep track of number of books. Made changes to addBook and removeBook. Added helper functions getBookCount, and bookExist.
 * 
 * Edit: Quan Luu 12/1/2024
 * Updated addBook and removeBook. Deleted helper function bookExist.
 */
import java.util.ArrayList;
import java.util.List;

public class BookManager
{
    private List<Book> books;
    private int bookCount; // Keeps track of how many book is in the catalog

    public BookManager()
    {
        this.books = new ArrayList<>();
        bookCount = 0;
    }

    public void addBook(String title, String author, String genre, String ISBN) 
    {
    	Book add = searchBookByISBN(ISBN);
    	
    	if(add != null) // If the book does not exist (using ISBN to check) then add new Book.
    		throw new IllegalArgumentException("Book with the given ISBN already exists.");
    	
		Book newBook = new Book(title, author, genre, ISBN);
		books.add(newBook);
		bookCount++;
    }

    public void removeBook(String ISBN)
    {
    	Book remove = searchBookByISBN(ISBN); // Checks to see if the book exist before removal.
    	if(remove != null) // If the book does exist remove it.
    	{
    		books.remove(remove);
    		bookCount--;
    	}
    }

    public Book searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null; // Book not found
    }

    public List<Book> getAllBooks()
    {
        return books;
    }

    // Method to search for a book by ISBN
    public Book searchBookByISBN(String ISBN) 
    {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null; // Book not found
    }
    
    
    public int getBookCount() // Returns the number of book in the catalog.
    {
    	return bookCount;
    }
    
}