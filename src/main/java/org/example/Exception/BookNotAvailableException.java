package org.example.Exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String bookId) {
        super("Book not available: " + bookId);
    }
}
