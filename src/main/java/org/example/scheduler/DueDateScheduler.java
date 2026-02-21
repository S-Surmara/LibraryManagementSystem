package org.example.scheduler;

import org.example.Entity.BookingRecord;
import org.example.persistance.BookingRepository;
import org.example.service.FineCalculator;
import org.example.service.NotificationService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DueDateScheduler {
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    private final FineCalculator fineCalculator;

    public DueDateScheduler(BookingRepository bookingRepository,
                            NotificationService notificationService,
                            FineCalculator fineCalculator) {
        this.bookingRepository   = bookingRepository;
        this.notificationService = notificationService;
        this.fineCalculator      = fineCalculator;
    }

    // Call this daily (cron / @Scheduled in Spring)
    public void run() {
        LocalDate today = LocalDate.now();

        List<BookingRecord> dueToday = bookingRepository.findByDueDate(today);
        dueToday.forEach(r ->
                notificationService.sendDueReminder(r.getUserId(), r.getBookId())
        );

        List<BookingRecord> overdue = bookingRepository.findOverdue(today);
        overdue.forEach(r -> {
            long days  = ChronoUnit.DAYS.between(r.getDueDate(), today);
            double fine = fineCalculator.calculate(days);
            notificationService.sendOverdueNotice(r.getUserId(), r.getBookId(), fine);
        });
    }
}

