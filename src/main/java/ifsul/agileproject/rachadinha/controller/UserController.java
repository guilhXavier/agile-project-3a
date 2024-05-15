package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserLoginDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserRespostaDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.UserNotFoundException;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

  //Buscar user por ID
  @GetMapping("{id}")
  public ResponseEntity<UserRespostaDTO> getUserByID(@PathVariable Long id) {
    Optional<User> usuario = userService.findUserById(id);

    if (usuario.isPresent()) {
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario.get()), HttpStatus.OK);
    }

    throw new UserNotFoundException();
  }

  //Cadastrar user
  @PostMapping("/cadastro")
  public ResponseEntity<UserRespostaDTO> saveUser(@RequestBody UserDTO userDTO) {
    User usuario = userService.saveUser(userDTO);
    return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario), HttpStatus.CREATED);
  }

  //Deletar usuário pelo ID
  @DeleteMapping("{id}")
  public ResponseEntity deleteUserByID(@PathVariable Long id) {
    userService.deleteUserById(id);
    return new ResponseEntity("Usuário deletado", HttpStatus.OK);
  }

  //Atualizar usuário pelo ID
  @PatchMapping("{id}")
  public ResponseEntity updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
    return new ResponseEntity(userService.updateUser(userDTO), HttpStatus.OK);
  }

  //Busca todos usuários
  @GetMapping("/findAll")
  public ResponseEntity<List<User>> findAll() {
    List<User> userList = userService.findAll();
    return new ResponseEntity<>(userList, HttpStatus.OK);
  }

  //Login do usuário com EMAIL e PASSWORD
  @PostMapping("/login")
  public ResponseEntity<UserRespostaDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
    User userLogged = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());

    if (userLogged != null) {
      return new ResponseEntity(UserRespostaDTO.transformaEmDTO(userLogged), HttpStatus.OK);
    }

    throw new UserNotFoundException();

  }

}
