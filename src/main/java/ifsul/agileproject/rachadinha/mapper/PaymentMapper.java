package ifsul.agileproject.rachadinha.mapper;

import java.util.function.Function;

import ifsul.agileproject.rachadinha.domain.dto.PaymentDTO;
import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.PaymentId;

public class PaymentMapper implements Function<PaymentDTO, Payment> {

    @Override
    public Payment apply(PaymentDTO dto) {
        return Payment.builder()
                .id(PaymentId.builder()
                        .rachaId(dto.getRachaId())
                        .userId(dto.getUserId())
                        .build())
                .userSaidPaid(dto.hasUserSaidPaid())
                .confirmedByOwner(dto.isConfirmedByOwner())
                .isOwner(dto.isOwner())
                .build();
    }
}
