package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;

public record OwnerDTO(long id, String name, String email) {

  public static OwnerDTO transformaEmDTO(User user){
    return new OwnerDTO(user.getId(), user.getName(), user.getEmail());
  }
}
