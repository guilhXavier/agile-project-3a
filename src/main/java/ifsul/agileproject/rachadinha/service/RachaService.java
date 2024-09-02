package ifsul.agileproject.rachadinha.service;

import java.util.List;
import java.util.Optional;

import ifsul.agileproject.rachadinha.domain.dto.RachaDetailsDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;

public interface RachaService {

	Racha saveRacha(RachaDetailsDTO rachaDTO, Long ownerId);

	Optional<Racha> findRachaById(Long id);

	List<Racha> findAll();

	void deleteRachaById(Long id, Long loggerUserId);

	Racha updateRacha(RachaDetailsDTO rachaDetailsDTO, Racha racha);

	Racha updateRacha(RachaDetailsDTO rachaDetailsDTO, Racha racha, Long loggerUserId);

	Racha findRachaByInvite(String invite);

	List<Racha> findRachaByOwner(Long ownerId);

	void addMemberToRacha(Long rachaId, Long userId, String password);

	void removeMemberFromRacha(Long rachaId, Long userId);

	List<Racha> getRachasByUserId(Long userId);

	User getRachaOwner(Long rachaId);

	void userSaidPaid(Long rachaId, Long userId);

	void confirmedByOwner(Long rachaId, Long userId);

	void confirmedByOwner(Long rachaId, Long userId, Long loggedUserId);

  void calcularPortionPerMember(Racha racha);
}
