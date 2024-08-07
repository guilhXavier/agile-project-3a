package ifsul.agileproject.rachadinha.exceptions;

public class IncorrectRachaPasswordException extends RuntimeException {

    public IncorrectRachaPasswordException(Long id) {
        super("Senha incorreta para o racha com id \"" + id + "\"");
    }
    
}
