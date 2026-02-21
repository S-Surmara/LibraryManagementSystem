package org.example.persistance;

import org.example.Entity.BookingRecord;
import org.example.enums.BookingStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BookingRepository {
    private final ConcurrentHashMap<String, BookingRecord> store = new ConcurrentHashMap<>();

    public void save(BookingRecord record)               { store.put(record.getRecordId(), record); }

    public Optional<BookingRecord> findById(String id)  { return Optional.ofNullable(store.get(id)); }

    public int countActiveByUser(String userId) {
        return (int) store.values().stream()
                .filter(r -> r.getUserId().equals(userId)
                        && r.getStatus() == BookingStatus.BORROWED)
                .count();
    }

    public Optional<BookingRecord> findActiveByUserAndBook(String userId, String bookId) {
        return store.values().stream()
                .filter(r -> r.getUserId().equals(userId)
                        && r.getBookId().equals(bookId)
                        && r.getStatus() == BookingStatus.BORROWED)
                .findFirst();
    }

    public List<BookingRecord> findByDueDate(LocalDate date) {
        return store.values().stream()
                .filter(r -> r.getDueDate().equals(date)
                        && r.getStatus() == BookingStatus.BORROWED)
                .collect(Collectors.toList());
    }

    public List<BookingRecord> findOverdue(LocalDate today) {
        return store.values().stream()
                .filter(r -> r.getDueDate().isBefore(today)
                        && r.getStatus() == BookingStatus.BORROWED)
                .collect(Collectors.toList());
    }
}
