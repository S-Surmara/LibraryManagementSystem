package org.example.service;


import org.example.Entity.Book;
import org.example.Exception.BookNotFoundException;
import org.example.persistance.BookRepository;

import java.util.List;

public class SearchService {
    private final BookRepository bookRepository;

    public SearchService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> searchByTitle(String title)   { return bookRepository.searchByTitle(title); }
    public List<Book> searchByAuthor(String author) { return bookRepository.searchByAuthor(author); }

    public Book searchByIsbn(String isbn) {
        return bookRepository.searchByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("ISBN: " + isbn));
    }
}

