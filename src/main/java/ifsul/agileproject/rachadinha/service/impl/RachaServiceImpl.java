package ifsul.agileproject.rachadinha.service.impl;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.mapper.RachaMapper;
import ifsul.agileproject.rachadinha.utils.RandomCodeGenerator;
import ifsul.agileproject.rachadinha.repository.PaymentRepository;
import ifsul.agileproject.rachadinha.repository.RachaRepository;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import ifsul.agileproject.rachadinha.service.RachaService;

@Service
@AllArgsConstructor
public class RachaServiceImpl implements RachaService {

  private RachaRepository rachaRepository;

  private UserRepository userRepository;

  private PaymentRepository paymentRepository;

  private RachaMapper rachaMapper;

  @Override
  public User getRachaOwner(Long rachaId) {
    return rachaRepository.findOwnerByRachaId(rachaId);
  }

  @Override
  public Racha saveRacha(RachaRegisterDTO rachaDTO) {
    if (!userRepository.existsById(rachaDTO.getOwnerId())) {
      throw new UserNotFoundException(rachaDTO.getOwnerId());
    }
    Racha racha = rachaMapper.apply(rachaDTO);
    racha.setInviteLink(generateUniqueInviteLink());
    return rachaRepository.save(racha);
  }

  @Override
  public Optional<Racha> findRachaById(Long id) {
    if (!rachaRepository.existsById(id)) {
      throw new RachaNotFoundException(id);
    }
    return rachaRepository.findById(id);
  }

  public List<Racha> findAll() {
    return rachaRepository.findAll();
  }

  @Override
  public void deleteRachaById(Long id, Long loggedUserId) {
    if (!rachaRepository.existsById(id)) {
      throw new RachaNotFoundException(id);
    }
    if (!userRepository.existsById(loggedUserId)) {
      throw new UserNotFoundException(loggedUserId);
    }
    if (getRachaOwner(id).getId() != loggedUserId) {
      throw new ForbiddenUserException(loggedUserId);
    }
    rachaRepository.deleteById(id);
  }

  @Override
  public Racha updateRacha(RachaUpdateDTO updatedRachaDTO, Racha originalRacha) {
    if (updatedRachaDTO.getName() != null) {
      originalRacha.setName(updatedRachaDTO.getName());
    }
    if (updatedRachaDTO.getDescription() != null) {
      originalRacha.setDescription(updatedRachaDTO.getDescription());
    }
    if (updatedRachaDTO.getGoal() != null) {
      originalRacha.setGoal(updatedRachaDTO.getGoal());
    }
    if (updatedRachaDTO.getPassword() != null) {
      originalRacha.setPassword(updatedRachaDTO.getPassword());
    }
    return rachaRepository.save(originalRacha);
  }

  @Override
  public Racha updateRacha(RachaUpdateDTO updatedRachaDTO, Racha originalRacha, Long loggedUserId) {
    if (getRachaOwner(originalRacha.getId()).getId() != loggedUserId) {
      throw new ForbiddenUserException(loggedUserId);
    }
    return updateRacha(updatedRachaDTO, originalRacha);
  }

  private String generateUniqueInviteLink() {
    String invite;
    do {
      invite = RandomCodeGenerator.generateRandomCode();
    } while (rachaRepository.findByInviteLink(invite) != null);
    return invite;
  }

  @Override
  public Racha findRachaByInvite(String invite) {
    Racha racha = rachaRepository.findByInviteLink(invite);
    if (racha == null) {
      throw new RachaNotFoundException();
    }
    return racha;
  }

  @Override
  public List<Racha> findRachaByOwner(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    List<Racha> rachas = rachaRepository.findByMembersId(userId);
    rachas.removeIf(racha -> racha.getOwner().getId() != userId);
    return rachas;
  }

  @Override
  public void addMemberToRacha(Long rachaId, Long userId, String password) {
    if (!rachaRepository.existsById(rachaId)) {
      throw new RachaNotFoundException(rachaId);
    }
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    Racha racha = rachaRepository.findById(rachaId).get();
    User user = userRepository.findById(userId).get();
    Payment existingPayment = paymentRepository.findByRachaAndUser(racha, user);
    if (!racha.getStatus().equals(Status.OPEN)) {
      throw new ClosedRachaException(rachaId);
    }
    if (existingPayment != null) {
      throw new UserAlreadyInRachaException(userId, rachaId);
    }
    if (!racha.getPassword().equals(password)) {
      throw new IncorrectRachaPasswordException(rachaId);
    }
    Payment payment = Payment.builder()
        .racha(racha)
        .user(user)
        .userSaidPaid(false)
        .confirmedByOwner(true)
        .isOwner(false)
        .build();
    paymentRepository.save(payment);
  }

  @Override
  public void removeMemberFromRacha(Long rachaId, Long userId) {
    if (!rachaRepository.existsById(rachaId)) {
      throw new RachaNotFoundException(rachaId);
    }
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    Racha racha = rachaRepository.findById(rachaId).get();
    User user = userRepository.findById(userId).get();
    Payment existingPayment = paymentRepository.findByRachaAndUser(racha, user);
    if (!racha.getStatus().equals(Status.OPEN)) {
      throw new ClosedRachaException(rachaId);
    }
    if (existingPayment == null) {
      throw new UserNotInRachaException(userId, rachaId);
    }
    paymentRepository.delete(existingPayment);
  }

  public List<Racha> getRachasByUserId(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    return rachaRepository.findByMembersId(userId);
  }
}
