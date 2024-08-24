package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.mapper.UserMapper;
import lombok.Data;

@Data
public class UserResponseDTO {

  private Long id;
  private String name;
  private String email;

  @Deprecated
  public static UserResponseDTO transformaEmDTO(User user) {
    UserMapper userMapper = new UserMapper();
    return userMapper.toResponseDTO(user);
  }
}
