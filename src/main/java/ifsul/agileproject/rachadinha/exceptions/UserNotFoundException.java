package ifsul.agileproject.rachadinha.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(Long id) {
    super("Não foi possível encontrar o usuário de id " + id);
  }
}
