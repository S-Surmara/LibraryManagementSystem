package org.example.service;
import org.example.observer.NotificationObserver;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void addObserver(NotificationObserver observer) { observers.add(observer); }

    public void sendDueReminder(String userId, String bookId) {
        observers.forEach(o -> o.notifyDueReminder(userId, bookId));
    }

    public void sendOverdueNotice(String userId, String bookId, double fine) {
        observers.forEach(o -> o.notifyOverdue(userId, bookId, fine));
    }
}

