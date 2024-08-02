package ifsul.agileproject.rachadinha.exceptions;

public class RachaNotFoundException extends RuntimeException {

  public RachaNotFoundException(Long id) {
    super("Racha com id \"" + id + "\" não encontrado");
  }
    
}
