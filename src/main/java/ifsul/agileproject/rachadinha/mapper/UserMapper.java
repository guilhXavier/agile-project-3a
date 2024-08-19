package ifsul.agileproject.rachadinha.mapper;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Component
public class UserMapper implements Function<UserDTO, User> {

  @Override
  public User apply(UserDTO dto) {
    return User.builder()
      .name(dto.getName())
      .email(dto.getEmail())
      .password(dto.getPassword())
      .rachas(new ArrayList<>())
      .build();
  }
}
