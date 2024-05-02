package ifsul.agileproject.rachadinha.mapper;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserMapper implements Function<UserDTO, User> {

  @Override
  public User apply(UserDTO dto) {
    return User.builder()
      .name(dto.getName())
      .email(dto.getEmail())
      .password(dto.getPassword())
      .build();
  }
}
