package ifsul.agileproject.rachadinha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.ForbiddenUserException;
import ifsul.agileproject.rachadinha.exceptions.RachaNotFoundException;
import ifsul.agileproject.rachadinha.mapper.RachaMapper;
import ifsul.agileproject.rachadinha.repository.RachaRepository;
import ifsul.agileproject.rachadinha.service.RachaService;

@Service
public class RachaServiceImpl implements RachaService {

  @Autowired
  private RachaRepository rachaRepository;

  @Autowired
  private RachaMapper rachaMapper;
  
  @Override
  public Racha saveRacha(RachaRegisterDTO rachaDTO) {
    Racha racha = rachaMapper.apply(rachaDTO);
    return rachaRepository.save(racha);
  }

  @Override
  public void save(Racha racha){
    rachaRepository.save(racha);
  }

  @Override
  public Optional<Racha> findRachaById(Long id) {
    if (!rachaRepository.existsById(id)) {
      throw new RachaNotFoundException(id);
    }
    return rachaRepository.findById(id);
  }

  public List<Racha> findAll(){
    return rachaRepository.findAll();
  }

  @Override
  public void deleteRachaById(Long id) {
    if (!rachaRepository.existsById(id)) {
      throw new RachaNotFoundException(id);
    }
    rachaRepository.deleteById(id);
  }

  @Override
  public void deleteRachaById(Long id, Long loggedUserId) {
    if (!rachaRepository.existsById(id)) {
      throw new RachaNotFoundException(id);
    }
    Racha racha = rachaRepository.findById(id).get();
    if (racha.getOwner().getId() != loggedUserId) {
      throw new ForbiddenUserException(loggedUserId);
    }
    rachaRepository.deleteById(id);
  }

  @Override
  public Racha updateRacha(RachaUpdateDTO rachaUpdateDTO, Racha racha) {
		if (rachaUpdateDTO.getName() != null) {
			racha.setName(rachaUpdateDTO.getName());
		}
		if (rachaUpdateDTO.getDescription() != null) {
			racha.setDescription(rachaUpdateDTO.getDescription());
		}
		if (rachaUpdateDTO.getGoal() != null) {
			racha.setGoal(rachaUpdateDTO.getGoal());
		}
		if (rachaUpdateDTO.getPassword() != null) {
			racha.setPassword(rachaUpdateDTO.getPassword());
		}
		return rachaRepository.save(racha);
	}

  @Override
  public Racha updateRacha(RachaUpdateDTO rachaUpdateDTO, Racha racha, Long loggedUserId) {
    if (racha.getOwner().getId() != loggedUserId) {
      throw new ForbiddenUserException(loggedUserId);
    }
    return updateRacha(rachaUpdateDTO, racha);
  }

  @Override
  public Racha findRachaByStatus(Status status) {
    return rachaRepository.findByStatus(status);
  }

  @Override
  public List<Racha> findRachaByOwner(User owner) {
    return rachaRepository.findByOwner(owner);
  }

  @Override
  public List<Racha> findRachaByOwner(Long id) {
    User owner = new User();
    owner.setId(id);
    return rachaRepository.findByOwner(owner);
  }
}
