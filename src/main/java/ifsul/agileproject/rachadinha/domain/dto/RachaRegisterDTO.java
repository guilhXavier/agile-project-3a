package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.User;
import lombok.Data;

@Data
public class RachaRegisterDTO {
	private String name;
	private String description;
	private String password;
	private Double goal;
	private User owner;
}
