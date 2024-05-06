package ifsul.agileproject.rachadinha.domain.dto;

import java.util.Date;

import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import lombok.Data;
import lombok.NonNull;

@Data
public class RachaDTO {

	private Long id;
	private String name;
	private String description;
	private Double goal;
	private User owner;
	private Status status;
	private Date created_at;
	private String inviteLink;
	private String password;
}
