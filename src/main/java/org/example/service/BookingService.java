package org.example.service;


import org.example.Entity.Book;
import org.example.Entity.BookingRecord;
import org.example.Exception.BookNotAvailableException;
import org.example.Exception.BookNotFoundException;
import org.example.Exception.BorrowLimitExceededException;
import org.example.config.LibraryConfig;
import org.example.enums.BookingStatus;
import org.example.persistance.BookRepository;
import org.example.persistance.BookingRepository;
import org.example.strategy.payment.PaymentStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookRepository bookRepository;
    private final FineCalculator    fineCalculator;

    private final ConcurrentHashMap<String, ReentrantLock> userLocks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ReentrantLock> bookLocks = new ConcurrentHashMap<>();


    public BookingService(BookingRepository bookingRepository,
                          BookRepository bookRepository,
                          FineCalculator fineCalculator) {
        this.bookingRepository = bookingRepository;
        this.bookRepository    = bookRepository;
        this.fineCalculator    = fineCalculator;
    }

    public BookingRecord borrowBook(String userId, String bookId, int days) {
        ReentrantLock userLock = userLocks.computeIfAbsent(userId, k -> new ReentrantLock());
        ReentrantLock bookLock = bookLocks.computeIfAbsent(bookId, k -> new ReentrantLock());
        userLock.lock();
        try {
            bookLock.lock();       // 👈 also lock the book
            try {

                // 1. Enforce borrow limit
                int activeCount = bookingRepository.countActiveByUser(userId);
                if (activeCount >= LibraryConfig.MAX_BORROW_LIMIT) {
                    throw new BorrowLimitExceededException("Max limit reached.");
                }

                // 2. Check availability  ← now thread-safe
                Book book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new BookNotFoundException(bookId));
                if (!book.isAvailable()) {
                    throw new BookNotAvailableException(bookId);
                }

                // 3. Mark unavailable & save
                book.setAvailable(false);
                bookRepository.save(book);

                LocalDate dueDate = LocalDate.now().plusDays(days);
                BookingRecord record = new BookingRecord(
                        UUID.randomUUID().toString(), userId, bookId, LocalDate.now(), dueDate
                );
                bookingRepository.save(record);
                return record;

            } finally {
                bookLock.unlock();
            }
        } finally {
            userLock.unlock();
        }
    }

    public void returnBook(String userId, String bookId, PaymentStrategy paymentStrategy) {
        BookingRecord record = bookingRepository
                .findActiveByUserAndBook(userId, bookId)
                .orElseThrow(() -> new RuntimeException(
                        "No active booking found for user: " + userId + ", book: " + bookId
                ));

        LocalDate today  = LocalDate.now();
        record.setReturnDate(today);
        record.setStatus(BookingStatus.RETURNED);

        // Calculate & pay fine if overdue
        if (today.isAfter(record.getDueDate())) {
            long overdueDays = today.toEpochDay() - record.getDueDate().toEpochDay();
            double fine      = fineCalculator.calculate(overdueDays);
            record.setFine(fine);
            paymentStrategy.pay(fine);
        }

        // Free the book
        bookRepository.findById(bookId).ifPresent(book -> {
            book.setAvailable(true);
            bookRepository.save(book);
        });

        bookingRepository.save(record);
    }

    public List<Book> getBooksByUser(String userId) {
        return bookingRepository.findOverdue(LocalDate.MIN).stream()  // all records
                .filter(r -> r.getUserId().equals(userId)
                        && r.getStatus() == BookingStatus.BORROWED)
                .map(r -> bookRepository.findById(r.getBookId()).orElse(null))
                .filter(b -> b != null)
                .collect(Collectors.toList());
    }
}

