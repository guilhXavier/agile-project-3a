package ifsul.agileproject.rachadinha.session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

  Optional<UserSession> findByToken(String token);

  void deleteByToken(String token);

  boolean existsByUserId(Long userId);

  UserSession findByUserId(Long userId);
}

