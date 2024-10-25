package fr.salah.exceptions;

public class UserNotValidException extends RuntimeException {
    public UserNotValidException() {
        super("User is not valid");
    }
}
