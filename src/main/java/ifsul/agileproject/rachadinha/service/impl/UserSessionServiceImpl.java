package ifsul.agileproject.rachadinha.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ifsul.agileproject.rachadinha.domain.entity.UserSession;
import ifsul.agileproject.rachadinha.exceptions.UserNotLoggedInException;
import ifsul.agileproject.rachadinha.repository.UserSessionRepository;
import ifsul.agileproject.rachadinha.service.UserSessionService;

@Service
@AllArgsConstructor
public class UserSessionServiceImpl implements UserSessionService {

  private UserSessionRepository userSessionRepository;

  public void createSession(UserSession session) {
    userSessionRepository.save(session);
  }

  public UserSession getSessionByToken(String token) {
    if (userSessionRepository.findByToken(token) == null) {
      throw new UserNotLoggedInException();
    }

    return userSessionRepository.findByToken(token);
  }

  public boolean isSessionValid(String token) {
    UserSession session = getSessionByToken(token);
    return session != null && !session.isExpired();
  }

  @Transactional
  public void invalidateSession(String token) {
    if (userSessionRepository.findByToken(token) == null) {
      throw new UserNotLoggedInException();
    }
    userSessionRepository.deleteByToken(token);
  }

  public boolean existsByUserId(Long userId){
    return userSessionRepository.existsByUserId(userId);
  }

  public UserSession findByUserId(Long userId){
    return userSessionRepository.findByUserId(userId);
  }
}
