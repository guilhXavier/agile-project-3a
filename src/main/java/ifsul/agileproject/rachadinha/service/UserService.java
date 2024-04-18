package ifsul.agileproject.rachadinha.service;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    Assert.notNull(userRepository, "UserRepository must not be null!");
    this.userRepository = userRepository;
  }

  public User saveUser(User user){
    return userRepository.save(user);
  }

  public User findUserByID(int id){
    return userRepository.findByID(id);
  }

  public boolean existsUserByID(int id){
    return userRepository.existsById(id);
  }

  public void deleteUserByID(int id){
    userRepository.deleteById(id);
  }

  public List<User> findAll(){
    return userRepository.findAll();
  }

  public User login(String email, String password){
    return userRepository.findByEmailAndPassword(email, password);
  }

  public User findUserByEmail(String email){
    return userRepository.findByEmail(email);
  }
}
