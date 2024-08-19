package ifsul.agileproject.rachadinha.session;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserSessionService {

  private UserSessionRepository userSessionRepository;

  public void createSession(UserSession session) {
    userSessionRepository.save(session);
  }

  public UserSession getSessionByToken(String token) {
    return userSessionRepository.findByToken(token)
      .orElse(null);
  }

  public boolean isSessionValid(String token) {
    UserSession session = getSessionByToken(token);
    return session != null && !session.isExpired();
  }

  @Transactional
  public void invalidateSession(String token) {
    userSessionRepository.deleteByToken(token);
  }

  public boolean existsByUserId(Long userId){
    return userSessionRepository.existsByUserId(userId);
  }

  public UserSession findByUserId(Long userId){
    return userSessionRepository.findByUserId(userId);
  }
}
