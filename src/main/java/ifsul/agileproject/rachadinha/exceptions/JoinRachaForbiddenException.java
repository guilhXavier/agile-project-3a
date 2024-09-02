package ifsul.agileproject.rachadinha.exceptions;

public class JoinRachaForbiddenException extends RuntimeException {
    
    public JoinRachaForbiddenException() {
        super("Usuário não pode entrar no racha");
    }

    public JoinRachaForbiddenException(Long rachaId) {
        super("Usuário não pode entrar no racha de id " + rachaId);
    }

    public JoinRachaForbiddenException(String message) {
        super(message);
    }
    
}
