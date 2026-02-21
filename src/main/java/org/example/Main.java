package org.example;

import org.example.Entity.Book;
import org.example.Entity.Librarian;
import org.example.Entity.Member;
import org.example.strategy.payment.UPIPaymentStrategy;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LibraryManagementSystem lms = LibraryManagementSystem.getInstance();

        // ── Setup Users ──────────────────────────────────
        Member member    = new Member("U001", "Arjun", "arjun@email.com", "9999999999");
        Librarian librarian = new Librarian("L001", "Priya", "priya@email.com", "8888888888");
        lms.addUser(member);
        lms.addUser(librarian);

        System.out.println("Is Librarian (L001): " + lms.isLibrarian("L001")); // true
        System.out.println("Is Librarian (U001): " + lms.isLibrarian("U001")); // false

        // ── Setup Books ───────────────────────────────────
        Book book1 = new Book("B001", "Clean Code", "Robert Martin", "978-0132350884", LocalDate.of(2008, 8, 1));
        Book book2 = new Book("B002", "Effective Java", "Joshua Bloch", "978-0134685991", LocalDate.of(2018, 1, 1));
        lms.addBook(book1);
        lms.addBook(book2);

        // ── Search ────────────────────────────────────────
        System.out.println("Search by author: " + lms.searchByAuthor("Joshua Bloch").get(0).getTitle());

        // ── Borrow ────────────────────────────────────────
        var record = lms.borrowBook("U001", "B001", 7);
        System.out.println("Borrowed: " + record.getBookId() + " | Due: " + record.getDueDate());

        // ── Daily Scheduler (simulate) ────────────────────
        lms.runDailyScheduler();

        // ── Return with UPI payment ───────────────────────
        lms.returnBook("U001", "B001", new UPIPaymentStrategy());
        System.out.println("Book returned successfully.");
    }
}