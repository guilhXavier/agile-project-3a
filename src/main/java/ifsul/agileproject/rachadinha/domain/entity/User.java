//Classe Usuário
package ifsul.agileproject.rachadinha.domain.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@Table(name = "Usuario")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  @NonNull
  private String name;

  @Column(name = "email", unique = true)
  @NonNull
  private String email;

  @Column(name = "password")
  @NonNull
  private String password;

  @OneToMany(mappedBy = "owner")
  private Set<Racha> ownRachas;

  @ManyToMany
  @JoinTable(
    name = "racha_members",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "racha_id"))
  private List<Racha> rachas;

}
