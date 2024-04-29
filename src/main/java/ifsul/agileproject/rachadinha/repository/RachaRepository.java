package ifsul.agileproject.rachadinha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ifsul.agileproject.rachadinha.domain.entity.Racha;

@Repository
public interface RachaRepository extends JpaRepository<Racha, Long>{

    Racha findByInviteLink(String inviteLink);

}
