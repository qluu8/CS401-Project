/*
 * Author: Andreas Sotiras 11/27/24
 * I created a basic template with a bookmanager class. Please look over this @Christian
 * And add based off what seems to be needed.
 * 
 * Edit: Quan Luu 11/27/2024
 * Added new variable bookCount to keep track of number of books. Made changes to addBook and removeBook. Added helper functions getBookCount, and bookExist.
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
    	int exsists = bookExist(ISBN);
    	
    	if(exsists == 0) // If the book does not exist (using ISBN to check) then add new Book.
    	{
    		Book newBook = new Book(title, author, genre, ISBN);
    		books.add(newBook);
    		bookCount++;
    	}
    	else // If the book exist (using ISBN to check) make changes to the already existing book.
    	{
    		books.get(exsists).setTitle(title);
    		books.get(exsists).setAuthor(author);
    		books.get(exsists).setGenre(genre);
    		books.get(exsists).setISBN(ISBN);
    	}
    }

    public void removeBook(String ISBN)
    {
    	int exsists = bookExist(ISBN); // Checks to see if the book exist before removal.
    	if(exsists != 0) // If the book does exist remove it.
    	{
    		books.removeIf(book -> book.getISBN().equals(ISBN));
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
    
    public int bookExist(String ISBN) // Checks to see if the book exist in the list. Returns the position of the found ISBN.
    {
    	for(int i = 0; i < bookCount; i++)
    		if(books.get(i).getISBN().equalsIgnoreCase(ISBN))
    			return i;
    	
    	return 0; // Returns 0 if the book does not exist.
    		
    }
    void addBook(Book newBook) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addBook(Book newBook) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addBook'");
    }
}