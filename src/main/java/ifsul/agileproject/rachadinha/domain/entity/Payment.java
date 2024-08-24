package ifsul.agileproject.rachadinha.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "racha_members")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "racha_id")
    @NotNull
    private Racha racha;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @Column(name = "user_said_paid")
    private boolean userSaidPaid;

    @Column(name = "confirmed_by_owner")
    private boolean confirmedByOwner;

    @Column(name = "is_owner")
    private boolean isOwner;

    public boolean hasUserSaidPaid() {
        return userSaidPaid;
    }
}