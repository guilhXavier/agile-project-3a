package ifsul.agileproject.rachadinha.service.impl;

import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.mapper.RachaMapper;
import ifsul.agileproject.rachadinha.utils.RandomCodeGenerator;
import ifsul.agileproject.rachadinha.repository.RachaRepository;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import ifsul.agileproject.rachadinha.service.RachaService;

@Service
@AllArgsConstructor
public class RachaServiceImpl implements RachaService {

  private RachaRepository rachaRepository;

  private UserRepository userRepository;

  private RachaMapper rachaMapper;

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

  public List<Racha> findAll(){
    return rachaRepository.findAll();
  }

  @Override
  public void deleteRachaById(Long id, Long loggedUserId) {
    if (!rachaRepository.existsById(id)) {
      throw new RachaNotFoundException(id);
    }
    Racha racha = rachaRepository.findById(id).get();
    if (!userRepository.existsById(loggedUserId)) {
      throw new UserNotFoundException(loggedUserId);
    }
    if (racha.getOwner().getId() != loggedUserId) {
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
    if (originalRacha.getOwner().getId() != loggedUserId) {
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
    return rachaRepository.findByOwnerId(userId);
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
    if (racha.getMembers().contains(user)) {
			throw new UserAlreadyInRachaException(userId, rachaId);
		}
    if (!racha.getPassword().equals(password)) {
      throw new IncorrectRachaPasswordException(rachaId);
    }
    racha.getMembers().add(user);
    user.getRachas().add(racha);
    rachaRepository.save(racha);
    userRepository.save(user);
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
    if (!racha.getMembers().contains(user)) {
      throw new UserNotInRachaException(userId, rachaId);
    }
    racha.getMembers().remove(user);
    user.getRachas().remove(racha);
    rachaRepository.save(racha);
    userRepository.save(user);
  }

  public List<Racha> getRachasByUserId(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    return rachaRepository.findByMembersId(userId);
  }

  public List<Racha> getAllRachasByUserVinculo(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }

    List<Racha> rachasAsMember = rachaRepository.findByMembersId(userId);
    List<Racha> rachasAsOwner = rachaRepository.findByOwnerId(userId);

    Set<Racha> allRachas = new HashSet<>(rachasAsMember);
    allRachas.addAll(rachasAsOwner);

    return new ArrayList<>(allRachas);
  }
}
