package org.example.persistance;

import org.example.Entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BookRepository {
    private final ConcurrentHashMap<String, Book> store = new ConcurrentHashMap<>();

    public void save(Book book)                         { store.put(book.getBookId(), book); }
    public Optional<Book> findById(String bookId)       { return Optional.ofNullable(store.get(bookId)); }
    public void remove(String bookId)                   { store.remove(bookId); }

    public List<Book> searchByTitle(String title) {
        return store.values().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return store.values().stream()
                .filter(b -> b.getAuthorName().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public Optional<Book> searchByIsbn(String isbn) {
        return store.values().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst();
    }

    public List<Book> findAll() { return new ArrayList<>(store.values()); }
}

