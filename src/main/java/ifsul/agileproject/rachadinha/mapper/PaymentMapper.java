package ifsul.agileproject.rachadinha.mapper;

import java.util.function.Function;

import ifsul.agileproject.rachadinha.domain.dto.PaymentDTO;
import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;

public class PaymentMapper implements Function<PaymentDTO, Payment> {

    @Override
    public Payment apply(PaymentDTO dto) {
        Racha racha = new Racha();
        racha.setId(dto.getRachaId());
        User user = new User();
        user.setId(dto.getUserId());

        return Payment.builder()
                .racha(racha)
                .user(user)
                .userSaidPaid(dto.hasUserSaidPaid())
                .confirmedByOwner(dto.isConfirmedByOwner())
                .isOwner(dto.isOwner())
                .build();
    }
}
