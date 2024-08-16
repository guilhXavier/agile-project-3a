package ifsul.agileproject.rachadinha.exceptions;

public class ForbiddenUserException extends RuntimeException {

  public ForbiddenUserException(String email) {
    super("Usuário com email \"" + email + "\" não tem permissão para realizar esta operação");
  }

  public ForbiddenUserException(Long id) {
    super("Usuário com id \"" + id + "\" não tem permissão para realizar esta operação");
  }
    
}
