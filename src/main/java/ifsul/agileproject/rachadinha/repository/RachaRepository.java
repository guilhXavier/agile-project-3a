package ifsul.agileproject.rachadinha.repository;

import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RachaRepository extends JpaRepository<Racha, Long> {

    Racha findByStatus(Status status);
}
