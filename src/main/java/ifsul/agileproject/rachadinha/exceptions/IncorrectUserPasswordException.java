package ifsul.agileproject.rachadinha.exceptions;

public class IncorrectUserPasswordException extends RuntimeException {
    
    public IncorrectUserPasswordException(String email) {
        super("Senha incorreta para o email \"" + email + "\"");
    }

}
