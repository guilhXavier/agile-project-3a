package ifsul.agileproject.rachadinha.service;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User saveUser(User user){
    return userRepository.save(user);
  }

  public Optional<User> findUserByID(int id){
    return userRepository.findById(id);
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
