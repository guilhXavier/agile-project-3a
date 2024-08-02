package ifsul.agileproject.rachadinha.service;

import java.util.List;
import java.util.Optional;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;

public interface RachaService {

	Racha saveRacha(RachaRegisterDTO rachaDTO);

	Optional<Racha> findRachaById(Long id);

  List<Racha> findAll();

	void deleteRachaById(Long id);

	void deleteRachaById(Long id, Long loggerUserId);

	Racha updateRacha(RachaUpdateDTO rachaUpdateDTO, Racha racha);

	Racha updateRacha(RachaUpdateDTO rachaUpdateDTO, Racha racha, Long loggerUserId);

	Racha findRachaByStatus(Status status);

	List<Racha> findRachaByOwner(User owner);

	List<Racha> findRachaByOwner(Long ownerId);

	void save(Racha racha);
}
