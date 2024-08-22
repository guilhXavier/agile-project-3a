package ifsul.agileproject.rachadinha.service.impl;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.mapper.UserMapper;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import ifsul.agileproject.rachadinha.service.UserService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  @Override
  public User saveUser(UserDTO userDTO) {
    if (userRepository.existsByEmail(userDTO.getEmail())) {
      throw new EmailAlreadyUsedException(userDTO.getEmail());
    }
    User user = userMapper.apply(userDTO);
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }
    return userRepository.findById(id);
  }

  @Override
  public void deleteUserById(Long id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException(id);
    }
    userRepository.deleteById(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User login(String email, String password) {
    if (!userRepository.existsByEmail(email)) {
      throw new UserNotFoundException(email);
    }
    User foundUser = userRepository.findByEmailAndPassword(email, password);
    if (foundUser == null) {
      throw new IncorrectUserPasswordException(email);
    }
    return foundUser;
  }

  @Override
  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User updateUser(UserDTO userDTO) {
    Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

    Predicate<String> isNotEmpty = (value) -> !value.isEmpty();

    if (!user.isPresent()) {
      throw new UserNotFoundException(userDTO.getEmail());
    }

    User updatedUser = user.get();

    Optional.ofNullable(userDTO.getName()).filter(isNotEmpty).ifPresent(updatedUser::setName);
    Optional.ofNullable(userDTO.getEmail()).filter(isNotEmpty).ifPresent(updatedUser::setEmail);
    Optional.ofNullable(userDTO.getPassword()).filter(isNotEmpty).ifPresent(updatedUser::setPassword);

    return userRepository.save(updatedUser);
  }

  @Override
  public User updateUser(UserDTO userDTO, Long loggedUserId) {
    Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
    if (!user.isPresent()) {
      throw new UserNotFoundException(userDTO.getEmail());
    }
    if (user.get().getId() != loggedUserId) {
      throw new ForbiddenUserException(loggedUserId);
    }
    return updateUser(userDTO);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public User resetPassword(Long userId, String oriPass, String newPass) {
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new UserNotFoundException(userId);
    }

    User usuario = user.get();

    if(!usuario.getPassword().equals(oriPass)){
      throw new IncorrectUserPasswordException(usuario.getEmail());
    }

    usuario.setPassword(newPass);

    return userRepository.save(usuario);
  }
}
