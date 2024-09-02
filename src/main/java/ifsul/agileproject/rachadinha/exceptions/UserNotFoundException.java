package ifsul.agileproject.rachadinha.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String email) {
    super("Usuário com email " + email + " não encontrado");
  }

  public UserNotFoundException(Long id) {
    super("Usuário com id " + id + " não encontrado");
  }
}
