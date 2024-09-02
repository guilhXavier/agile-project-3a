package ifsul.agileproject.rachadinha.domain.dto;

import lombok.Data;

/**
 * Represents the details of a User for gathering information through the
 * controller on user creation and update.
 */
@Data
public class UserDetailsDTO {

  private String name;
  private String email;
  private String password;

}
