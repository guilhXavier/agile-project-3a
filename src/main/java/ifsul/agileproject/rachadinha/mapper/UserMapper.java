package ifsul.agileproject.rachadinha.mapper;

import ifsul.agileproject.rachadinha.domain.dto.OwnerDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserDetailsDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UserMapper implements Mapper<UserDetailsDTO, User> {

  @Override
  public User toEntity(UserDetailsDTO dto) {
    return User.builder()
      .name(dto.getName())
      .email(dto.getEmail())
      .password(dto.getPassword())
      .rachas(new ArrayList<>())
      .build();
  }

  @Override
  public UserDetailsDTO toDTO(User entity) {
    UserDetailsDTO userDTO = new UserDetailsDTO();
    userDTO.setName(entity.getName());
    userDTO.setEmail(entity.getEmail());
    userDTO.setPassword(entity.getPassword());
    return userDTO;
  }

  public OwnerDTO toOwnerDTO(User entity) {
    OwnerDTO ownerDTO = new OwnerDTO();
    ownerDTO.setId(entity.getId());
    ownerDTO.setName(entity.getName());
    ownerDTO.setEmail(entity.getEmail());
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
