package ifsul.agileproject.rachadinha.exceptions;

public class ClosedRachaException extends RuntimeException {
    
    public ClosedRachaException() {
        super("O usuário não pode entrar ou sair de um racha fechado");
    }

    public ClosedRachaException(Long rachaId) {
        super("O usuário não pode entrar ou sair do racha " + rachaId + " pois ele está fechado");
    }
    
}
