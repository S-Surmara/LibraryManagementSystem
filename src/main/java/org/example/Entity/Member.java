package org.example.Entity;


import org.example.enums.MemberStatus;
import org.example.visitor.UserVisitor;

public class Member extends User {
    private MemberStatus status;

    public Member(String userId, String username, String email, String phoneNumber) {
        super(userId, username, email, phoneNumber);
        this.status = MemberStatus.ACTIVE;
    }

    @Override
    public void accept(UserVisitor visitor) {
        visitor.visit(this);
    }

    public MemberStatus getStatus()             { return status; }
    public void setStatus(MemberStatus status)  { this.status = status; }
}

