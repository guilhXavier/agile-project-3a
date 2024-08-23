package ifsul.agileproject.rachadinha.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "racha_members")
public class Payment {
    
    @EmbeddedId
    private PaymentId id;

    @ManyToOne
    @MapsId("rachaId")
    @JoinColumn(name = "racha_id")
    private Racha racha;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_said_paid")
    private boolean userSaidPaid;

    @Column(name = "confirmed_by_owner")
    private boolean confirmedByOwner;

    @Column(name = "is_owner")
    private boolean isOwner;

    public boolean isOwner() {
        return isOwner;
    }

    public User getUser() {
        return user;
    }
}

