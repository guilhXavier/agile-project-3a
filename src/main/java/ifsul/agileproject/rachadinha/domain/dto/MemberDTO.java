package ifsul.agileproject.rachadinha.domain.dto;

import lombok.Data;

@Data
public class MemberDTO {
    private Long userId;
    private String userName;
    private String email;
    private Long paymentId;
    private boolean userSaidPaid;
    private boolean confirmedByOwner;
    private boolean isOwner;
}
