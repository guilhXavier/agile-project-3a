package ifsul.agileproject.rachadinha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ifsul.agileproject.rachadinha.domain.entity.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

  UserSession findByToken(String token);

  void deleteByToken(String token);

  boolean existsByUserId(Long userId);

  UserSession findByUserId(Long userId);
}

