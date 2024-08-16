package ifsul.agileproject.rachadinha.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserLoginDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class UserController {

  private final UserServiceImpl userService;

  @Operation(summary = "Busca um usuário pelo ID", description = "Retorna os dados públicos de um usuário com base no ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  })
  @GetMapping("{id}")
  public ResponseEntity getUserByID(@PathVariable Long id) {
    try {
      Optional<User> usuario = userService.findUserById(id);
      return new ResponseEntity<UserResponseDTO>(UserResponseDTO.transformaEmDTO(usuario.get()), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com base nos dados passados no corpo da requisição")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
    @ApiResponse(responseCode = "400", description = "Email já em uso")
  })
  @PostMapping("/signup")
  public ResponseEntity saveUser(@RequestBody UserDTO userDTO) {
    try {
      User usuario = userService.saveUser(userDTO);
      return new ResponseEntity<UserResponseDTO>(UserResponseDTO.transformaEmDTO(usuario), HttpStatus.CREATED);
    } catch (EmailAlreadyUsedException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @Operation(summary = "Deleta um usuário", description = "Deleta um usuário com base no ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuário deletado"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  })
  @DeleteMapping("{id}")
  public ResponseEntity deleteUserByID(@PathVariable Long id) {
    try {
      userService.deleteUserById(id);
      return new ResponseEntity<String>("Usuário deletado", HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Atualiza um usuário", description = "Atualiza um usuário com base nos dados passados no corpo da requisição")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  })
  @PatchMapping("{id}")
  public ResponseEntity updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
    try {
      User usuario = userService.updateUser(userDTO);
      return new ResponseEntity<UserResponseDTO>(UserResponseDTO.transformaEmDTO(usuario), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Busca todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuários encontrados")
  })
  @GetMapping("/list/all")
  public ResponseEntity<List<UserResponseDTO>> findAll() {
    List<User> userList = userService.findAll();

    List<UserResponseDTO> listDTO = userList.stream()
        .map(UserResponseDTO::transformaEmDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<List<UserResponseDTO>>(listDTO, HttpStatus.OK);
  }

  @Operation(summary = "Faz login", description = "Faz login com base no email e senha passados no corpo da requisição")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    @ApiResponse(responseCode = "400", description = "Senha incorreta")
  })
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

  @Operation(summary = "Define uma nova senha", description = "Define uma nova senha para um usuário com base no ID")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
    @ApiResponse(responseCode = "400", description = "Senha original incorreta")
  })
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
