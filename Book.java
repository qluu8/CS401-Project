/* Author: Christian Amoranto
 * initial draft of Book class based on requiremnts doc 11/24/2024
 * 
 * Edit by Quan Luu 11/27/2024
 * Added functions to set title, author, genre, and ISBN.
 */
    
public class Book {
    private String title;
    private String author;
    private String genre;
    private String ISBN;
    private boolean isAvailable;
    private boolean isReserved;

    public Book(String title, String author, String genre, String ISBN, boolean isAvailable, boolean isReserved) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
        this.isReserved = isReserved;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public String toString() {
        return "Book Details:\n" +
                "Title: " + title + "\n" +
                "Author: " + author + "\n" +
                "Genre: " + genre + "\n" +
                "ISBN: " + ISBN + "\n" +
                "Status: " + (isAvailable ? "Available" : "Not Available") + "\n" +
                "Reserved: " + (isReserved ? "Yes" : "No");
    }
}

