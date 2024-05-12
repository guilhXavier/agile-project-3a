package ifsul.agileproject.rachadinha.domain.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RachaRegisterDTO {
	private String name;
	private String description;
	private String password;
	private Double goal;
  private Long ownerId;

}
