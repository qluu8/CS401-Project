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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private List<Book> books;
    private static final String BOOKS_FILE = "books.txt";

    public BookManager() {
        this.books = new ArrayList<>();
        loadBooksFromFile();
    }

    // Load books from the file
    void loadBooksFromFile() {
        File file = new File(BOOKS_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";"); // Use semicolon as delimiter
                if (parts.length == 6) {
                    String title = parts[0];
                    String author = parts[1];
                    String genre = parts[2];
                    String ISBN = parts[3];
                    boolean isAvailable = Boolean.parseBoolean(parts[4]);
                    boolean isReserved = Boolean.parseBoolean(parts[5]);
                    books.add(new Book(title, author, genre, ISBN, isAvailable, isReserved));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save books to the file
    void saveBooksToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.write(String.join(";", book.getTitle(), book.getAuthor(), book.getGenre(), book.getISBN(),
                        String.valueOf(book.isAvailable()), String.valueOf(book.isReserved())));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a book
    public void addBook(String title, String author, String genre, String ISBN) {
        if (searchBookByISBN(ISBN) != null) {
            throw new IllegalArgumentException("Book with the given ISBN already exists.");
        }

        Book newBook = new Book(title, author, genre, ISBN, true, false);
        books.add(newBook);
        saveBooksToFile(); // Save changes to file
    }

    // Remove a book
    public void removeBook(String ISBN) {
        Book book = searchBookByISBN(ISBN);
        if (book != null) {
            books.remove(book);
            saveBooksToFile(); 
        }
    }

    // Reserve a book
    public void reserveBook(String ISBN) {
        Book book = searchBookByISBN(ISBN);
        if (book != null && book.isAvailable() && !book.isReserved()) {
            book.setReserved(true);
            book.setAvailable(false);
            saveBooksToFile(); 
        }
    }

    // Search for a book by title
    public Book searchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Search for a book by ISBN
    public Book searchBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.getISBN().equals(ISBN)) {
                return book;
            }
        }
        return null;
    }

    // Get all books
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
}
