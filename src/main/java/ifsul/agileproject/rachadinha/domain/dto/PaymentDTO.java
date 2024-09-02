package ifsul.agileproject.rachadinha.domain.dto;

import lombok.Data;

@Data
public class PaymentDTO {

    private Long id;
    private boolean userSaidPaid;
    private boolean confirmedByOwner;
    private boolean isOwner;
    private Long userId;
    private Long rachaId;

    public boolean hasUserSaidPaid() {
        return userSaidPaid;
    }

}