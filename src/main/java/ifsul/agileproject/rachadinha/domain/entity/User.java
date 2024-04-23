//Classe Usuário
package ifsul.agileproject.rachadinha.domain.entity;

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

  //@ManyToMany
  //ArrayList<User> listaAmigos;

}
