package ifsul.agileproject.rachadinha.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifsul.agileproject.rachadinha.domain.dto.UserDetailsDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserLoginDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.domain.entity.UserSession;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.mapper.UserMapper;
import ifsul.agileproject.rachadinha.service.UserService;
import ifsul.agileproject.rachadinha.service.UserSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class UserController {

  private final UserService userService;

  private final UserSessionService sessionService;

  private final UserMapper userMapper;

  @Operation(summary = "Busca um usuário pelo ID", description = "Retorna os dados públicos de um usuário com base no ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  })
  @GetMapping("{id}")
  public ResponseEntity getUserByID(@PathVariable Long id) {
    try {
      Optional<User> usuario = userService.findUserById(id);
      return new ResponseEntity<UserResponseDTO>(userMapper.toResponseDTO(usuario.get()), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Busca o usuário logado", description = "Retorna os dados públicos do usuário logado")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @GetMapping("/me")
  public ResponseEntity getLoggedUser(@RequestHeader("rachadinha-login-token") String token) {
    try {
      UserSession session = sessionService.getSessionByToken(token);

      Optional<User> usuario = userService.findUserById(session.getUserId());
      return new ResponseEntity<UserResponseDTO>(userMapper.toResponseDTO(usuario.get()), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com base nos dados passados no corpo da requisição")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
      @ApiResponse(responseCode = "400", description = "Email já em uso")
  })
  @PostMapping("/signup")
  public ResponseEntity saveUser(@RequestBody UserDetailsDTO userDetailsInput) {
    try {
      User usuario = userService.saveUser(userDetailsInput);
      return new ResponseEntity<UserResponseDTO>(userMapper.toResponseDTO(usuario), HttpStatus.CREATED);
    } catch (EmailAlreadyUsedException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @Operation(summary = "Deleta o usuário logado", description = "Deleta o usuário logado")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário deletado"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @DeleteMapping()
  public ResponseEntity deleteUserByID(@RequestHeader("rachadinha-login-token") String token) {
    try {
      UserSession session = sessionService.getSessionByToken(token);

      userService.deleteUserById(session.getUserId());
      sessionService.invalidateSession(token);

      return new ResponseEntity<String>("Usuário deletado", HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Atualiza os dados do usuário logado", description = "Atualiza os dados do usuário logado com base nos dados passados no corpo da requisição")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @PatchMapping()
  public ResponseEntity updateUser(@RequestBody UserDetailsDTO userDetailsInput,
      @RequestHeader("rachadinha-login-token") String token) {
    try {
      UserSession session = sessionService.getSessionByToken(token);

      User usuario = userService.updateUser(userDetailsInput, session.getUserId());

      return new ResponseEntity<UserResponseDTO>(userMapper.toResponseDTO(usuario), HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
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
        .map(userMapper::toResponseDTO)
        .collect(Collectors.toList());

    return new ResponseEntity<List<UserResponseDTO>>(listDTO, HttpStatus.OK);
  }

  @Operation(summary = "Faz login", description = "Faz login com base no email e senha passados no corpo da requisição")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
      @ApiResponse(responseCode = "403", description = "Usuário já está logado"),
  })
  @PostMapping("/login")
  public ResponseEntity login(@RequestBody UserLoginDTO userLoginDTO) {
    try {
      User userLogged = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPassword());

      UserSession session = sessionService.findByUserId(userLogged.getId());

      if (session == null) {
        session = new UserSession(userLogged.getId());
        sessionService.createSession(session);
      } else {
        throw new UserAlreadyLoggedInException();
      }

      // Adding the token to the response header
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.set("rachadinha-login-token", session.getToken());

      return ResponseEntity.ok()
          .headers(responseHeaders)
          .body("Usuário logado.");

    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (IncorrectUserPasswordException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (UserAlreadyLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

  }

  @Operation(summary = "Faz logout", description = "Faz logout do usuário logado")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Logout efetuado com sucesso"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @PostMapping("/logout")
  public ResponseEntity logout(@RequestHeader("rachadinha-login-token") String token) {
    try {
      sessionService.invalidateSession(token);
      return new ResponseEntity<String>("Usuário deslogado.", HttpStatus.OK);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Define uma nova senha para o usuário logado", description = "Define uma nova senha para o usuário logado com base na senha original e na nova senha passadas nos parâmetros")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
      @ApiResponse(responseCode = "400", description = "Senha original incorreta"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @PatchMapping("/resetPass")
  public ResponseEntity resetPassword(@RequestHeader("rachadinha-login-token") String token,
      @RequestParam String oriPass,
      @RequestParam String newPass) {
    try {
      UserSession session = sessionService.getSessionByToken(token);

      userService.resetPassword(session.getUserId(), oriPass, newPass);
      return new ResponseEntity<String>("Senha alterada com sucesso.", HttpStatus.OK);

    } catch (IncorrectUserPasswordException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }
}