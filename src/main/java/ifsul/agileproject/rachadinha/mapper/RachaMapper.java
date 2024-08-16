package ifsul.agileproject.rachadinha.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;

import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;

@Component
@AllArgsConstructor
public class RachaMapper implements Function<RachaRegisterDTO, Racha> {

  UserService userService;
  
  @Override
  public Racha apply(RachaRegisterDTO dto) {

    User owner = userService.findUserById(dto.getOwnerId()).get();

    return Racha.builder()
      .name(dto.getName())
      .description(dto.getDescription())
      .password(dto.getPassword())
      .goal(dto.getGoal())
      .owner(owner)
      .members(new ArrayList<>())
      .balance(0.0)
      .status(Status.OPEN)
      .created_at(new Date())
      .build();
  }
  
}
