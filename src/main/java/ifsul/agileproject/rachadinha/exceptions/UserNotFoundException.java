package ifsul.agileproject.rachadinha.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(int id){
      super("Não foi possível encontrar o usuário " + id);
    }
}
