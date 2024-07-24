package ifsul.agileproject.rachadinha.service.impl;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.EmailAlreadyUsedException;
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
      throw new EmailAlreadyUsedException("Endereco de email ja utilizado");
    }
    User user = userMapper.apply(userDTO);
    return userRepository.save(user);
  }

  @Override
  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User login(String email, String password) {
    return userRepository.findByEmailAndPassword(email, password);
  }

  @Override
  public Optional<User> findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User updateUser(UserDTO userDTO) {
    Optional<User> user = userRepository.findByEmail(userDTO.getEmail());

    Predicate<String> isNotEmpty = (value) -> !value.isEmpty();

    if (user.isPresent()) {
      User updatedUser = user.get();

      Optional.ofNullable(userDTO.getName()).filter(isNotEmpty).ifPresent(updatedUser::setName);
      Optional.ofNullable(userDTO.getEmail()).filter(isNotEmpty).ifPresent(updatedUser::setEmail);
      Optional.ofNullable(userDTO.getPassword()).filter(isNotEmpty).ifPresent(updatedUser::setPassword);

      return userRepository.save(updatedUser);
    }
    throw new RuntimeException();
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }
}