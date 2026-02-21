package org.example.observer;

public interface NotificationObserver {
    void notifyDueReminder(String userId, String bookId);
    void notifyOverdue(String userId, String bookId, double fine);
}

