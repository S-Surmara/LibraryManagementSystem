package org.example.Exception;

public class BorrowLimitExceededException extends RuntimeException {
    public BorrowLimitExceededException(String msg) { super(msg); }
}
