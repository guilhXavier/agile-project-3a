package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserLoginDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.EmailAlreadyUsedException;
import ifsul.agileproject.rachadinha.exceptions.ErrorResponse;
import ifsul.agileproject.rachadinha.exceptions.UserNotFoundException;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

  // Buscar user por ID
  @GetMapping("{id}")
  public ResponseEntity<UserResponseDTO> getUserByID(@PathVariable Long id) {
    Optional<User> usuario = userService.findUserById(id);

    if (usuario.isPresent()) {
      return new ResponseEntity<>(UserResponseDTO.transformaEmDTO(usuario.get()), HttpStatus.OK);
    }

    throw new UserNotFoundException();
  }

  // Cadastrar user
  @PostMapping("/cadastro")
  public ResponseEntity<UserResponseDTO> saveUser(@RequestBody UserDTO userDTO) {
    try {
      User usuario = userService.saveUser(userDTO);
      return new ResponseEntity<>(UserResponseDTO.transformaEmDTO(usuario), HttpStatus.CREATED);
    } catch (EmailAlreadyUsedException e) {
      return new ResponseEntity(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

  }

  // Deletar usuário pelo ID
  @DeleteMapping("{id}")
  public ResponseEntity deleteUserByID(@PathVariable Long id) {
    userService.deleteUserById(id);
    return new ResponseEntity("Usuário deletado", HttpStatus.OK);
  }

  // Atualizar usuário pelo ID
  @PatchMapping("{id}")
  public ResponseEntity updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
    return new ResponseEntity(userService.updateUser(userDTO), HttpStatus.OK);
  }

  // Busca todos usuários
  @GetMapping("/findAll")
  public ResponseEntity<List<UserResponseDTO>> findAll() {
    List<User> userList = userService.findAll();

    List<UserResponseDTO> listDTO = userList.stream()
        .map(UserResponseDTO::transformaEmDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<>(listDTO, HttpStatus.OK);
  }

  // Login do usuário com EMAIL e PASSWORD
  @PostMapping("/login")
  public ResponseEntity<UserResponseDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
    User userLogged = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());

    if (userLogged != null) {
      return new ResponseEntity(UserResponseDTO.transformaEmDTO(userLogged), HttpStatus.OK);
    }

    throw new UserNotFoundException();

  }

  @PatchMapping("/resetPass")
  public ResponseEntity resetPassword(@RequestParam Long userId, String oriPass, String newPass){
    try{
      userService.resetPassword(userId, oriPass, newPass);
      return new ResponseEntity<>("Senha alterada com sucesso.", HttpStatus.OK);
    }catch (Exception ex){
      return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
