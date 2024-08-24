package ifsul.agileproject.rachadinha.domain.entity;

import java.util.List;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Racha")
public class Racha {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_racha")
  private Long id;

  @NonNull
  @Column(name = "name")
  private String name;

  @Column(name = "description", length = 255)
  private String description;

  @NonNull
  @Column(name = "password")
  private String password;

  @NonNull
  @Column(name = "goal")
  private Double goal;

  @Column(name = "balance")
  private Double balance;

  @OneToMany(mappedBy = "racha")
  private List<Payment> members;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Status status = Status.OPEN;

  @Column(name = "created_at")
  private Date created_at;

  @Column(name = "invite_link")
  private String inviteLink;

  public User getOwner() {
    for (Payment payment : members) {
      if (payment.isOwner()) {
        return payment.getUser();
      }
    }
    return null;
  }
}
