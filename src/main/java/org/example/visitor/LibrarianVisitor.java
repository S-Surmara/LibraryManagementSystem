package org.example.visitor;

import org.example.Entity.Librarian;
import org.example.Entity.Member;

public class LibrarianVisitor implements UserVisitor {
    private boolean isLibrarian = false;

    @Override
    public void visit(Member member)     { this.isLibrarian = false; }

    @Override
    public void visit(Librarian librarian) { this.isLibrarian = true; }

    public boolean isLibrarian() { return isLibrarian; }
}

