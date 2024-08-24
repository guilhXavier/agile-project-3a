package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;

public record OwnerDTO(long id, String name, String email) {

  public static OwnerDTO transformaEmDTO(User user){
    if (user == null) {
      throw new IllegalArgumentException("User não pode ser nulo");
    }
    return new OwnerDTO(user.getId(), user.getName(), user.getEmail());
  }
}
