package ifsul.agileproject.rachadinha.service;

import ifsul.agileproject.rachadinha.domain.entity.UserSession;

public interface UserSessionService {

    void createSession(UserSession session);

    UserSession getSessionByToken(String token);

    boolean isSessionValid(String token);

    void invalidateSession(String token);

    boolean existsByUserId(Long userId);

    UserSession findByUserId(Long userId);
}