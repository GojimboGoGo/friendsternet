package jbourne.demo.friendsternet.exception;

public class MissingUserException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Could not find user in database!";

    public MissingUserException() {
        super(DEFAULT_MESSAGE);
    }
}
