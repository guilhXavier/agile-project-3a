package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;
import lombok.Data;

@Data
public class UserDTO {

  private String name;
  private String email;
  private String password;

  public User transformaParaObjeto(){
    return new User(name, email, password);
  }
}
