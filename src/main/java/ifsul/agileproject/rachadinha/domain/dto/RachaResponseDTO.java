package ifsul.agileproject.rachadinha.domain.dto;

import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RachaResponseDTO {
  private Long id;
  private String name;
  private String description;
  private Double goal;
  private Double balance;
  private UserRespostaDTO owner;
  private Status status;
  private Date created_at;
  private String inviteLink;

  public static RachaResponseDTO transformarEmDto(Racha racha) {
    UserRespostaDTO owner = UserRespostaDTO.transformaEmDTO(racha.getOwner());
    return new RachaResponseDTO(racha.getId(), racha.getName(), racha.getDescription(), racha.getGoal(), racha.getBalance(), owner, racha.getStatus(), racha.getCreated_at(), racha.getInviteLink());
  }
}
