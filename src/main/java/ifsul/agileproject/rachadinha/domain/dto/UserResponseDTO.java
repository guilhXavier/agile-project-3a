package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class UserResponseDTO {

  private Long id;
  private String name;
  private String email;
  private List<RachaResponseDTO> rachas;

  public static UserResponseDTO transformaEmDTO(User user) {
    return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRachas()
      .stream()
      .map(RachaResponseDTO::transformarEmDto)
      .collect(Collectors.toList()));
  }
}
