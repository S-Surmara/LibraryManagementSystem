package org.example.Entity;

import org.example.visitor.UserVisitor;

public abstract class User {
    private final String userId;
    private final String username;
    private final String email;
    private final String phoneNumber;

    public User(String userId, String username, String email, String phoneNumber) {
        this.userId      = userId;
        this.username    = username;
        this.email       = email;
        this.phoneNumber = phoneNumber;
    }

    public abstract void accept(UserVisitor visitor);

    public String getUserId()      { return userId; }
    public String getUsername()    { return username; }
    public String getEmail()       { return email; }
    public String getPhoneNumber() { return phoneNumber; }
}

