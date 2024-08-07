package ifsul.agileproject.rachadinha.exceptions;

public class UserNotInRachaException extends RuntimeException {

    public UserNotInRachaException(Long userId, Long rachaId) {
        super("Usuário com id \"" + userId + "\" não está no racha com id \"" + rachaId + "\"");
    }

    public UserNotInRachaException() {
        super("Usuário não está no racha");
    }
    
}
