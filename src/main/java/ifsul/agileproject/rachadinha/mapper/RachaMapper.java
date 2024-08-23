package ifsul.agileproject.rachadinha.mapper;

import java.util.Date;
import java.util.function.Function;

import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.PaymentId;
import ifsul.agileproject.rachadinha.domain.entity.Racha;

@Component
@AllArgsConstructor
public class RachaMapper implements Function<RachaRegisterDTO, Racha> {

  UserService userService;

  @Override
  public Racha apply(RachaRegisterDTO dto) {

    User owner = userService.findUserById(dto.getOwnerId()).get();

    Racha racha = Racha.builder()
        .name(dto.getName())
        .description(dto.getDescription())
        .password(dto.getPassword())
        .goal(dto.getGoal())
        .balance(0.0)
        .status(Status.OPEN)
        .created_at(new Date())
        .build();

    Payment payment = Payment.builder()
        .id(PaymentId.builder()
            .rachaId(racha.getId())
            .userId(owner.getId())
            .build())
        .userSaidPaid(false)
        .confirmedByOwner(true)
        .isOwner(true)
        .build();

    racha.getMembers().add(payment);

    return racha;
  }

}
