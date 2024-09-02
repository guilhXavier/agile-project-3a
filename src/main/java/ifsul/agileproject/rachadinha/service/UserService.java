package ifsul.agileproject.rachadinha.service;

import ifsul.agileproject.rachadinha.domain.dto.UserDetailsDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  User saveUser(UserDetailsDTO userDTO);

  Optional<User> findUserById(Long id);

  void deleteUserById(Long id);

  List<User> findAll();

  User login(String email, String password);

  Optional<User> findUserByEmail(String email);

  User updateUser(UserDetailsDTO userDTO);

  User updateUser(UserDetailsDTO userDTO, Long loggedUserId);

  void save(User user);

  User resetPassword(Long userId, String oriPass, String newPass);
}
