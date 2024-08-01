package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserLoginDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class UserController {

  private final UserServiceImpl userService;

  // Buscar user por ID
  @GetMapping("{id}")
  public ResponseEntity getUserByID(@PathVariable Long id) {
    try {
      Optional<User> usuario = userService.findUserById(id);
      return new ResponseEntity<UserResponseDTO>(UserResponseDTO.transformaEmDTO(usuario.get()), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  // Cadastrar user
  @PostMapping("/signup")
  public ResponseEntity saveUser(@RequestBody UserDTO userDTO) {
    try {
      User usuario = userService.saveUser(userDTO);
      return new ResponseEntity<UserResponseDTO>(UserResponseDTO.transformaEmDTO(usuario), HttpStatus.CREATED);
    } catch (EmailAlreadyUsedException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  // Deletar usuário pelo ID
  @DeleteMapping("{id}")
  public ResponseEntity deleteUserByID(@PathVariable Long id) {
    try {
      userService.deleteUserById(id);
      return new ResponseEntity<String>("Usuário deletado", HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  // Atualizar usuário pelo ID
  @PatchMapping("{id}")
  public ResponseEntity updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
    try {
      User usuario = userService.updateUser(userDTO);
      return new ResponseEntity<UserResponseDTO>(UserResponseDTO.transformaEmDTO(usuario), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  // Busca todos usuários
  @GetMapping("/findAll")
  public ResponseEntity<List<UserResponseDTO>> findAll() {
    List<User> userList = userService.findAll();

    List<UserResponseDTO> listDTO = userList.stream()
        .map(UserResponseDTO::transformaEmDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<List<UserResponseDTO>>(listDTO, HttpStatus.OK);
  }

  // Login do usuário com EMAIL e PASSWORD
  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserLoginDTO userLoginDTO) {
    try {
      User userLogged = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());
      return new ResponseEntity<UserResponseDTO>(UserResponseDTO.transformaEmDTO(userLogged), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (IncorrectUserPasswordException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

  }

  @PatchMapping("/resetPass")
  public ResponseEntity resetPassword(@RequestParam Long userId, String oriPass, String newPass){
    try{
      userService.resetPassword(userId, oriPass, newPass);
      return new ResponseEntity<String>("Senha alterada com sucesso.", HttpStatus.OK);
    } catch (UserNotFoundException e){
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (IncorrectUserPasswordException e){
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

}
