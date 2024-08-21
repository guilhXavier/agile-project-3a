package ifsul.agileproject.rachadinha.exceptions;

public class UserAlreadyLoggedInException extends RuntimeException {
    
    public UserAlreadyLoggedInException() {
        super("Usuário já está logado");
    }
    
}
