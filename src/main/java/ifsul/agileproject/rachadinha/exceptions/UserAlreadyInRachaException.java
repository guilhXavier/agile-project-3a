package ifsul.agileproject.rachadinha.exceptions;

public class UserAlreadyInRachaException extends RuntimeException {

    public UserAlreadyInRachaException(Long userId, Long rachaId) {
        super("Usuário com id " + userId + " já está no racha com id " + rachaId);
    }

    public UserAlreadyInRachaException() {
        super("Usuário já está no racha");
    }
    
}