package ifsul.agileproject.rachadinha.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {
    
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}