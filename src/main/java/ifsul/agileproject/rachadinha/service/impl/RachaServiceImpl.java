package ifsul.agileproject.rachadinha.service.impl;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ifsul.agileproject.rachadinha.domain.dto.RachaDetailsDTO;
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
import ifsul.agileproject.rachadinha.service.UserService;

@Service
@AllArgsConstructor
public class RachaServiceImpl implements RachaService {

  private UserService userService;

  private RachaRepository rachaRepository;

  private UserRepository userRepository;

  private PaymentRepository paymentRepository;

  private RachaMapper rachaMapper;

  @Override
  public User getRachaOwner(Long rachaId) {
    Racha racha = rachaRepository.findById(rachaId).get();
    return racha.getOwner();
  }

  @Override
  public Racha saveRacha(RachaDetailsDTO rachaDTO, Long ownerId) {
    if (!userRepository.existsById(ownerId)) {
      throw new UserNotFoundException(ownerId);
    }
    User owner = userService.findUserById(ownerId).get();

    Racha racha = rachaMapper.toEntity(rachaDTO);
    racha.setInviteLink(generateUniqueInviteLink());

    rachaRepository.save(racha);

    Payment payment = Payment.builder()
        .racha(racha)
        .user(owner)
        .userSaidPaid(false)
        .confirmedByOwner(true)
        .isOwner(true)
        .build();

    paymentRepository.save(payment);

    if (racha.getMembers() == null) {
      racha.setMembers(new ArrayList<>());
    }
    racha.getMembers().add(payment);

    return rachaRepository.save(racha);
  }

  @Override
  public Optional<Racha> findRachaById(Long id) {
    if (!rachaRepository.existsById(id)) {
      throw new RachaNotFoundException(id);
    }
    return rachaRepository.findById(id);
  }

  @Override
  public Racha getRachaPage(Long idRacha, Long userId) {
    if (!rachaRepository.existsById(idRacha)) {
      throw new RachaNotFoundException(idRacha);
    }
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    Racha racha = rachaRepository.findById(idRacha).get();
    if (racha.getMembers().stream().noneMatch(payment -> payment.getUser().getId() == userId)) {
      throw new UserNotInRachaException(userId, idRacha);
    }
    return racha;
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
  public Racha updateRacha(RachaDetailsDTO updatedRachaDTO, Racha originalRacha) {
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
  public Racha updateRacha(RachaDetailsDTO updatedRachaDTO, Racha originalRacha, Long loggedUserId) {
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
      throw new JoinRachaForbiddenException(rachaId);
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
        .confirmedByOwner(false)
        .isOwner(false)
        .build();
    paymentRepository.save(payment);

    updateBalanceAndPortion(racha);
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
      throw new LeaveRachaForbiddenException(rachaId);
    }
    if (existingPayment == null) {
      throw new UserNotInRachaException(userId, rachaId);
    }
    if (existingPayment.isOwner()) {
      throw new LeaveRachaForbiddenException("O dono do racha não pode sair do racha");
    }
    paymentRepository.delete(existingPayment);
  }

  public List<Racha> getRachasByUserId(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    return rachaRepository.findByMembersId(userId);
  }

  @Override
  public void userSaidPaid(Long rachaId, Long userId) {
    Racha racha = rachaRepository.findById(rachaId).get();
    User user = userRepository.findById(userId).get();
    Payment payment = paymentRepository.findByRachaAndUser(racha, user);
    if (payment == null) {
      throw new UserNotInRachaException(userId, rachaId);
    }
    payment.setUserSaidPaid(true);
    paymentRepository.save(payment);

    updateBalanceAndPortion(racha);
  }

  @Override
  public void confirmedByOwner(Long rachaId, Long userId) {
    Racha racha = rachaRepository.findById(rachaId).get();
    User user = userRepository.findById(userId).get();
    Payment payment = paymentRepository.findByRachaAndUser(racha, user);
    if (payment == null) {
      throw new UserNotInRachaException(userId, rachaId);
    }
    if (!payment.isUserSaidPaid()) {
      payment.setUserSaidPaid(true);
    }
    payment.setConfirmedByOwner(true);
    paymentRepository.save(payment);

    if (racha.getBalance() >= racha.getGoal()) {
      racha.setStatus(Status.FINISHED);
      rachaRepository.save(racha);
    }
  }

  @Override
  public void confirmedByOwner(Long rachaId, Long userId, Long loggedUserId) {
    if (getRachaOwner(rachaId).getId() != loggedUserId) {
      throw new ForbiddenUserException(loggedUserId);
    }
    confirmedByOwner(rachaId, userId);
  }

  @Override
  public void updateBalanceAndPortion(Racha racha) {
    int totalMembros = racha.getMembers().size();
    double portionPerMember = racha.getGoal() / totalMembros;
    racha.setPortionPerMember(portionPerMember);

    long totalPagamentos = racha.getMembers().stream()
      .filter(Payment::isConfirmedByOwner)
      .count();
    double balanceAtualizado = totalPagamentos * portionPerMember;
    racha.setBalance(balanceAtualizado);

    rachaRepository.save(racha);
  }

  @Override
  public void closeRacha(long idRacha, Long userId) {
    Racha racha = rachaRepository.findById(idRacha).get();
    if (racha.getOwner().getId() != userId) {
      throw new ForbiddenUserException(userId);
    }
    racha.setStatus(Status.CLOSED);
    rachaRepository.save(racha);
  }
}
