package ifsul.agileproject.rachadinha.exceptions;

public class UserHasNotSaidPaidException extends RuntimeException {
    
    public UserHasNotSaidPaidException() {
        super("O usuário não disse que pagou");
    }
    
    public UserHasNotSaidPaidException(Long userId) {
        super("O usuário " + userId + " não disse que pagou");
    }
    
}
