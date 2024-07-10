package ifsul.agileproject.rachadinha.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifsul.agileproject.rachadinha.domain.dto.RachaDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
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
    return rachaRepository.findById(id);
  }

  @Override
  public void deleteRachaById(Long id) {
    rachaRepository.deleteById(id);
  }

  @Override
  public Racha updateRacha(RachaDTO rachaDTO) {
    Optional<Racha> existingRacha = rachaRepository.findById(rachaDTO.getId());

    if (existingRacha.isPresent()) {
      Racha updatedRacha = existingRacha.get();
      updatedRacha.setName(rachaDTO.getName());
      updatedRacha.setDescription(rachaDTO.getDescription());
      updatedRacha.setGoal(rachaDTO.getGoal());
      updatedRacha.setPassword(rachaDTO.getPassword());
      updatedRacha.setStatus(rachaDTO.getStatus());

      return rachaRepository.save(updatedRacha);
    }
    throw new RuntimeException();
  }

  @Override
  public Racha findRachaByStatus(Status status) {
    return rachaRepository.findByStatus(status);
  }

  @Override
  public List<Racha> findRachaByOwner(User owner) {
    return rachaRepository.findByOwner(owner);
  }
}
