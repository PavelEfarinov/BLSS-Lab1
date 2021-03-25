package ru.blss.lab1.exception;

public class UnauthorizedUserException extends Exception {
    public UnauthorizedUserException() {
        super("Invalid user token");
    }
    public UnauthorizedUserException(String message) {
        super(message);
    }
}
