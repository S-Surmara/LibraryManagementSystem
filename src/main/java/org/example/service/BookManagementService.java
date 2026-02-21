package org.example.service;


import org.example.Entity.Book;
import org.example.Exception.BookNotFoundException;
import org.example.persistance.BookRepository;

public class BookManagementService {
    private final BookRepository bookRepository;

    public BookManagementService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book)        { bookRepository.save(book); }

    public void removeBook(String bookId) { bookRepository.remove(bookId); }

    public Book getBook(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }
}

