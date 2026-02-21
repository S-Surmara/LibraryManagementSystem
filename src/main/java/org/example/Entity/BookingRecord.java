package org.example.Entity;

import java.time.LocalDate;
import org.example.enums.BookingStatus;

public class BookingRecord {
    private final String      recordId;
    private final String      userId;
    private final String      bookId;
    private final LocalDate   borrowDate;
    private final LocalDate   dueDate;
    private LocalDate         returnDate;
    private BookingStatus     status;
    private double            fine;

    public BookingRecord(String recordId, String userId, String bookId,
                         LocalDate borrowDate, LocalDate dueDate) {
        this.recordId   = recordId;
        this.userId     = userId;
        this.bookId     = bookId;
        this.borrowDate = borrowDate;
        this.dueDate    = dueDate;
        this.status     = BookingStatus.BORROWED;
        this.fine       = 0.0;
    }

    public String      getRecordId()   { return recordId; }
    public String      getUserId()     { return userId; }
    public String      getBookId()     { return bookId; }
    public LocalDate   getBorrowDate() { return borrowDate; }
    public LocalDate   getDueDate()    { return dueDate; }
    public LocalDate   getReturnDate() { return returnDate; }
    public BookingStatus getStatus()   { return status; }
    public double      getFine()       { return fine; }

    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setStatus(BookingStatus status)     { this.status = status; }
    public void setFine(double fine)                { this.fine = fine; }
}
