package org.example.visitor;


import org.example.Entity.Librarian;
import org.example.Entity.Member;

public interface UserVisitor {
    void visit(Member member);
    void visit(Librarian librarian);
}

