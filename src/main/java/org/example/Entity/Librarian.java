package org.example.Entity;

import org.example.visitor.UserVisitor;

public class Librarian extends User {

    public Librarian(String userId, String username, String email, String phoneNumber) {
        super(userId, username, email, phoneNumber);
    }

    @Override
    public void accept(UserVisitor visitor) {
        visitor.visit(this);
    }

    public boolean canManageCatalog() { return true; }
}

