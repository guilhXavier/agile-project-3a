package ifsul.agileproject.rachadinha.repository;

import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RachaRepository extends JpaRepository<Racha, Long> {

  Racha findByStatus(Status status);

  Racha findByInviteLink(String invite);

  @Query("SELECT r FROM Racha r JOIN r.members m WHERE m.user.id = :userId")
  List<Racha> findByMembersId(@Param("userId") Long userId);

  @Query(value = "SELECT * FROM racha_members WHERE racha_id = :rachaId AND user_id = :userId", nativeQuery = true)
  User findOwnerByRachaId(@Param("rachaId") Long rachaId);

}
