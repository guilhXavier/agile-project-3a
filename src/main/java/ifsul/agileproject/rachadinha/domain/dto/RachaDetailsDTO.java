package ifsul.agileproject.rachadinha.domain.dto;

import lombok.Data;

/**
 * Represents the details of a Racha for gathering information from the user
 * through the controller on racha creation and update.
 */
@Data
public class RachaDetailsDTO {
	private String name;
	private String description;
	private String password;
	private Double goal;
}
