package ifsul.agileproject.rachadinha.service;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  User saveUser(UserDTO userDTO);
  Optional<User> findUserById(Long id);
  void deleteUserById(Long id);
  List<User> findAll();
  User login(String email, String password);
  Optional<User> findUserByEmail(String email);
  User updateUser(UserDTO userDTO);
  void save(User user);
  User resetPassword(Long userId, String oriPass, String newPass);
}
