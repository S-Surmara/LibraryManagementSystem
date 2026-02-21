package org.example;


import org.example.Entity.Book;
import org.example.Entity.BookingRecord;
import org.example.Entity.User;
import org.example.observer.EmailNotificationObserver;
import org.example.persistance.BookRepository;
import org.example.persistance.BookingRepository;
import org.example.persistance.UserRepository;
import org.example.scheduler.DueDateScheduler;
import org.example.service.*;
import org.example.strategy.payment.PaymentStrategy;

import java.util.List;

public class LibraryManagementSystem {

    private static volatile LibraryManagementSystem instance;

    private final UserManagementService userManagementService;
    private final BookManagementService bookManagementService;
    private final BookingService bookingService;
    private final SearchService searchService;
    private final NotificationService notificationService;
    private final DueDateScheduler dueDateScheduler;

    private LibraryManagementSystem() {
        UserRepository    userRepo    = new UserRepository();
        BookRepository bookRepo    = new BookRepository();
        BookingRepository bookingRepo = new BookingRepository();
        FineCalculator    fineCalc    = new FineCalculator();

        this.userManagementService = new UserManagementService(userRepo);
        this.bookManagementService = new BookManagementService(bookRepo);
        this.bookingService        = new BookingService(bookingRepo, bookRepo, fineCalc);
        this.searchService         = new SearchService(bookRepo);

        this.notificationService   = new NotificationService();
        this.notificationService.addObserver(new EmailNotificationObserver());

        this.dueDateScheduler      = new DueDateScheduler(bookingRepo, notificationService, fineCalc);
    }

    public static LibraryManagementSystem getInstance() {
        if (instance == null) {
            synchronized (LibraryManagementSystem.class) {
                if (instance == null) instance = new LibraryManagementSystem();
            }
        }
        return instance;
    }

    // ── User ──────────────────────────────────────────────
    public void addUser(User user)              { userManagementService.addUser(user); }
    public void removeUser(String userId)       { userManagementService.removeUser(userId); }
    public boolean isLibrarian(String userId)   { return userManagementService.isLibrarian(userId); }

    // ── Books ─────────────────────────────────────────────
    public void addBook(Book book)              { bookManagementService.addBook(book); }
    public void removeBook(String bookId)       { bookManagementService.removeBook(bookId); }

    // ── Search ────────────────────────────────────────────
    public List<Book> searchByTitle(String t)   { return searchService.searchByTitle(t); }
    public List<Book> searchByAuthor(String a)  { return searchService.searchByAuthor(a); }
    public Book searchByIsbn(String isbn)       { return searchService.searchByIsbn(isbn); }

    // ── Booking ───────────────────────────────────────────
    public BookingRecord borrowBook(String userId, String bookId, int days) {
        return bookingService.borrowBook(userId, bookId, days);
    }
    public void returnBook(String userId, String bookId, PaymentStrategy paymentStrategy) {
        bookingService.returnBook(userId, bookId, paymentStrategy);
    }
    public List<Book> getBooksByUser(String userId) {
        return bookingService.getBooksByUser(userId);
    }

    // ── Scheduler (call daily) ────────────────────────────
    public void runDailyScheduler()             { dueDateScheduler.run(); }
}

