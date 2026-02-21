package org.example.Entity;

import java.time.LocalDate;

public class Book {
    private final String bookId;
    private final String title;
    private final String authorName;
    private final String isbn;
    private final LocalDate publicationDate;
    private boolean isAvailable;

    public Book(String bookId, String title, String authorName,
                String isbn, LocalDate publicationDate) {
        this.bookId          = bookId;
        this.title           = title;
        this.authorName      = authorName;
        this.isbn            = isbn;
        this.publicationDate = publicationDate;
        this.isAvailable     = true;
    }

    public String  getBookId()          { return bookId; }
    public String  getTitle()           { return title; }
    public String  getAuthorName()      { return authorName; }
    public String  getIsbn()            { return isbn; }
    public LocalDate getPublicationDate(){ return publicationDate; }
    public boolean isAvailable()        { return isAvailable; }
    public void    setAvailable(boolean available) { this.isAvailable = available; }
}
