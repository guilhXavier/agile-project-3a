package ifsul.agileproject.rachadinha.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("Não foi possível encontrar o usuário");
  }
}
