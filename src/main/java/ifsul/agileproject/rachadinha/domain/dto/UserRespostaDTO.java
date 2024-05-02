package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class UserRespostaDTO {

  private Long id;
  private String name;
  private String email;

  public static UserRespostaDTO transformaEmDTO(User user) {
    return new UserRespostaDTO(user.getId(), user.getName(), user.getEmail());
  }

}
