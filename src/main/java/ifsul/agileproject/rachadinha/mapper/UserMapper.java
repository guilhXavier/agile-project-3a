package ifsul.agileproject.rachadinha.mapper;

import ifsul.agileproject.rachadinha.domain.dto.OwnerDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper implements Mapper<UserDTO, User> {

  @Override
  public User toEntity(UserDTO dto) {
    return User.builder()
      .name(dto.getName())
      .email(dto.getEmail())
      .password(dto.getPassword())
      .rachas(new ArrayList<>())
      .build();
  }

  @Override
  public UserDTO toDTO(User entity) {
    UserDTO userDTO = new UserDTO();
    userDTO.setName(entity.getName());
    userDTO.setEmail(entity.getEmail());
    userDTO.setPassword(entity.getPassword());
    return userDTO;
  }

  public OwnerDTO toOwnerDTO(User entity) {
    OwnerDTO ownerDTO = new OwnerDTO();
    ownerDTO.setName(entity.getName());
    ownerDTO.setEmail(entity.getEmail());
    ownerDTO.setPassword(entity.getPassword());
    return ownerDTO;
  }

  public UserResponseDTO toResponseDTO(User user) {
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(user.getId());
    userResponseDTO.setName(user.getName());
    userResponseDTO.setEmail(user.getEmail());
    return userResponseDTO;
  }
}
