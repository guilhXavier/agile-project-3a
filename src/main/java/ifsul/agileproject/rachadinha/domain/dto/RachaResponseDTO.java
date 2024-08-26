package ifsul.agileproject.rachadinha.domain.dto;

import java.util.Date;

import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.mapper.RachaMapper;
import lombok.Data;

/**
 * The default response DTO for Racha entity. It is used to return public
 * information about a racha and a summary about the owner.
 */
@Data
public class RachaResponseDTO {
  private Long id;
  private String name;
  private String description;
  private Double goal;
  private Double balance;
  private OwnerDTO owner;
  private Status status;
  private Date created_at;
  private String inviteLink;

  @Deprecated
  public static RachaResponseDTO transformarEmDto(Racha racha) {
    RachaMapper rachaMapper = new RachaMapper();
    return rachaMapper.toResponseDTO(racha);
  }
}
