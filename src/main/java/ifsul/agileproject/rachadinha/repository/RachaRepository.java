package ifsul.agileproject.rachadinha.repository;

import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;

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
}
