package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import lombok.Data;

@Data
public class RachaDTO {

	private Long id;
	private String name;
	private String description;
	private User owner;
	private Status status;
	private String inviteLink;

}
