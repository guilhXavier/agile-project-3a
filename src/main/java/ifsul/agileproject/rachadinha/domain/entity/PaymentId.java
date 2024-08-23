package ifsul.agileproject.rachadinha.domain.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Builder;

@Embeddable
@Builder
@SuppressWarnings("unused")
public class PaymentId implements Serializable {

    private Long rachaId;
    private Long userId;

}
