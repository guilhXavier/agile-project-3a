package ifsul.agileproject.rachadinha.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import ifsul.agileproject.rachadinha.domain.dto.OwnerDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaDetailsDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.service.UserService;

@Component
public class RachaMapper implements Mapper<RachaDetailsDTO, Racha> {

  UserService userService;

  @Override
  public Racha toEntity(RachaDetailsDTO dto) {
    return Racha.builder()
        .name(dto.getName())
        .description(dto.getDescription())
        .password(dto.getPassword())
        .goal(dto.getGoal())
        .balance(0.0)
        .status(Status.OPEN)
        .created_at(new Date())
        .build();
  }

  @Override
  public RachaDetailsDTO toDTO(Racha entity) {
    RachaDetailsDTO rachaRegisterDTO = new RachaDetailsDTO();
    rachaRegisterDTO.setName(entity.getName());
    rachaRegisterDTO.setDescription(entity.getDescription());
    rachaRegisterDTO.setGoal(entity.getGoal());
    rachaRegisterDTO.setPassword(entity.getPassword());
    return rachaRegisterDTO;
  }

  public RachaResponseDTO toResponseDTO(Racha entity) {
    UserMapper userMapper = new UserMapper();
    OwnerDTO owner = userMapper.toOwnerDTO(entity.getOwner());

    RachaResponseDTO rachaResponseDTO = new RachaResponseDTO();
    rachaResponseDTO.setId(entity.getId());
    rachaResponseDTO.setName(entity.getName());
    rachaResponseDTO.setDescription(entity.getDescription());
    rachaResponseDTO.setGoal(entity.getGoal());
    rachaResponseDTO.setBalance(entity.getBalance());
    rachaResponseDTO.setOwner(owner);
    rachaResponseDTO.setStatus(entity.getStatus());
    rachaResponseDTO.setCreated_at(entity.getCreated_at());
    rachaResponseDTO.setInviteLink(entity.getInviteLink());
    return rachaResponseDTO;
  }
}
