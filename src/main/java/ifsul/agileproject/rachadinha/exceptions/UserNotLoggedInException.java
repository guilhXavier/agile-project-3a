package ifsul.agileproject.rachadinha.exceptions;

public class UserNotLoggedInException extends RuntimeException {
    
    public UserNotLoggedInException() {
        super("Usuário não está logado");
    }
    
}
