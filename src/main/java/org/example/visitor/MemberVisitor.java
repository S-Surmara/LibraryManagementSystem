package org.example.visitor;


import org.example.Entity.Librarian;
import org.example.Entity.Member;

public class MemberVisitor implements UserVisitor {
    private Member resolvedMember = null;

    @Override
    public void visit(Member member)       { this.resolvedMember = member; }

    @Override
    public void visit(Librarian librarian) { this.resolvedMember = null; }

    public Member getMember() { return resolvedMember; }
}

