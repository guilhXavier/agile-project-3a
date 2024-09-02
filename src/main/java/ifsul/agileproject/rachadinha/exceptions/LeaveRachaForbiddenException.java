package ifsul.agileproject.rachadinha.exceptions;

public class LeaveRachaForbiddenException extends RuntimeException {
    
    public LeaveRachaForbiddenException() {
        super("O usuário não pode sair do racha");
    }

    public LeaveRachaForbiddenException(Long rachaId) {
        super("O usuário não pode sair do racha de id " + rachaId);
    }

    public LeaveRachaForbiddenException(String message) {
        super(message);
    }
    
}
