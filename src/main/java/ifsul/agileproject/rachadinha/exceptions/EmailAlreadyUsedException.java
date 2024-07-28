package ifsul.agileproject.rachadinha.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {
    
    public EmailAlreadyUsedException(String email) {
        super("Endereco de email \"" + email + "\" já utilizado");
    }
}