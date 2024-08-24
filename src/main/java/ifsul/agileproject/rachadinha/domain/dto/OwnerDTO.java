package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.mapper.UserMapper;
import lombok.Data;

@Data
public class OwnerDTO {

  private String name;
  private String email;
  private String password;

  @Deprecated
  public static OwnerDTO transformaEmDTO(User user) {
    UserMapper userMapper = new UserMapper();
    return userMapper.toOwnerDTO(user);
  }
}
