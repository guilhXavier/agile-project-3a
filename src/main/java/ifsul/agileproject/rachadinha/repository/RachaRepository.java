package ifsul.agileproject.rachadinha.repository;

import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RachaRepository extends JpaRepository<Racha, Long> {

	Racha findByStatus(Status status);

	List<Racha> findByOwner(User owner);
}
