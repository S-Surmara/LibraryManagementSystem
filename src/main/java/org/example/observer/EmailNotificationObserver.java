package org.example.observer;

public class EmailNotificationObserver implements NotificationObserver {
    @Override
    public void notifyDueReminder(String userId, String bookId) {
        System.out.println("[EMAIL] Reminder: Book " + bookId
                + " is due today for user " + userId);
    }

    @Override
    public void notifyOverdue(String userId, String bookId, double fine) {
        System.out.println("[EMAIL] Overdue: Book " + bookId
                + " — Fine for user " + userId + " = ₹" + fine);
    }
}

