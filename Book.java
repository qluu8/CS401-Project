public class Book {
    private String title;
    private String author;
    private String genre;
    private String ISBN;
    private boolean isAvailable;
    private boolean isReserved;

    public Book(String title, String author, String genre, String ISBN) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.ISBN = ISBN;
        this.isAvailable = true; 
        this.isReserved = false; 
    }
   
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public void setReserved(boolean reserved) {
        this.isReserved = reserved;
    }

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
