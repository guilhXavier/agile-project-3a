package ifsul.agileproject.rachadinha.domain.dto;

import java.util.Date;
import java.util.List;

import ifsul.agileproject.rachadinha.domain.entity.Status;
import lombok.Data;

@Data
public class RachaDTO {
	private Long id;
	private String name;
	private String description;
	private Double goal;
	private Status status;
	private Date created_at;
	private String inviteLink;
	private List<MemberDTO> members;
}
