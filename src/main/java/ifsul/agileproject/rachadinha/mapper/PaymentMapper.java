package ifsul.agileproject.rachadinha.mapper;

import ifsul.agileproject.rachadinha.domain.dto.MemberDTO;
import ifsul.agileproject.rachadinha.domain.dto.PaymentDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;

public class PaymentMapper implements Mapper<PaymentDTO, Payment> {

    @Override
    public Payment toEntity(PaymentDTO dto) {
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

    @Override
    public PaymentDTO toDTO(Payment entity) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setRachaId(entity.getRacha().getId());
        paymentDTO.setUserId(entity.getUser().getId());
        paymentDTO.setUserSaidPaid(entity.hasUserSaidPaid());
        paymentDTO.setConfirmedByOwner(entity.isConfirmedByOwner());
        paymentDTO.setOwner(entity.isOwner());
        return paymentDTO;
    }

    public MemberDTO toMemberDTO(Payment entity) {
        UserMapper userMapper = new UserMapper();
        UserResponseDTO user = userMapper.toResponseDTO(entity.getUser());
        
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId(user.getId());
        memberDTO.setUserName(user.getName());
        memberDTO.setEmail(user.getEmail());
        memberDTO.setPaymentId(entity.getId());
        memberDTO.setUserSaidPaid(entity.hasUserSaidPaid());
        memberDTO.setConfirmedByOwner(entity.isConfirmedByOwner());
        memberDTO.setOwner(entity.isOwner());
        return memberDTO;
    }
}
