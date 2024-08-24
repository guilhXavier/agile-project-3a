package ifsul.agileproject.rachadinha.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifsul.agileproject.rachadinha.domain.dto.RachaDetailsDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaResponseDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.UserSession;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.service.impl.RachaServiceImpl;
import ifsul.agileproject.rachadinha.service.impl.UserSessionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/racha")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class RachaController {

  private final RachaServiceImpl rachaService;

  private final UserSessionServiceImpl sessionService;

  @Operation(summary = "Busca um racha pelo ID", description = "Retorna os dados de um racha com base no ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Racha encontrado"),
      @ApiResponse(responseCode = "404", description = "Racha não encontrado")
  })
  @GetMapping("{idRacha}")
  public ResponseEntity findRachaByID(@PathVariable Long idRacha) {
    try {
      Racha racha = rachaService.findRachaById(idRacha).orElseThrow(() -> new RachaNotFoundException(idRacha));
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(racha);
      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.OK);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Cria um racha do usuário logado", description = "Cria um racha com base nas informações passadas no corpo da requisição")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Racha criado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @PostMapping("/create")
  public ResponseEntity createRacha(@RequestBody RachaDetailsDTO rachaDTO,
      @RequestHeader("rachadinha-login-token") String token) {
    try {
      UserSession userSession = sessionService.getSessionByToken(token);

      RachaRegisterDTO rachaRegisterDTO = new RachaRegisterDTO();
      rachaRegisterDTO.setName(rachaDTO.getName());
      rachaRegisterDTO.setDescription(rachaDTO.getDescription());
      rachaRegisterDTO.setPassword(rachaDTO.getPassword());
      rachaRegisterDTO.setGoal(rachaDTO.getGoal());
      rachaRegisterDTO.setOwnerId(userSession.getUserId());

      Racha racha = rachaService.saveRacha(rachaRegisterDTO);
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(racha);

      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.CREATED);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Lista todos os rachas cujo usuário logado participa ou criou", description = "Retorna uma lista com os rachas que o usuário logado participa ou criou")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Rachas encontrados com sucesso"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @GetMapping("/list/user")
  public ResponseEntity getRachasByUserId(@RequestHeader("rachadinha-login-token") String token) {
    try {
      UserSession userSession = sessionService.getSessionByToken(token);

      List<Racha> listRachas = rachaService.getRachasByUserId(userSession.getUserId());

      List<RachaResponseDTO> listRachasDTO = listRachas.stream()
          .map(RachaResponseDTO::transformarEmDto)
          .toList();

      return new ResponseEntity<List<RachaResponseDTO>>(listRachasDTO, HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Lista todos os rachas cujo usuário logado é o dono", description = "Retorna uma lista com todos os rachas cujo usuário logado é o dono")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Rachas encontrados com sucesso"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @GetMapping("/list/owner")
  public ResponseEntity findByOwner(@RequestHeader("rachadinha-login-token") String token) {
    try {
      UserSession userSession = sessionService.getSessionByToken(token);

      List<Racha> rachas = rachaService.findRachaByOwner(userSession.getUserId());
      List<RachaResponseDTO> rachasResponseDto = rachas.stream()
          .map(RachaResponseDTO::transformarEmDto)
          .collect(Collectors.toList());
      return new ResponseEntity<List<RachaResponseDTO>>(rachasResponseDto, HttpStatus.OK);

    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Lista todos os rachas", description = "Retorna uma lista com todos os rachas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Rachas encontrados com sucesso")
  })
  @GetMapping("/list/all")
  public ResponseEntity findAll() {
    List<Racha> rachaList = rachaService.findAll();

    List<RachaResponseDTO> listDTO = rachaList.stream()
        .map(RachaResponseDTO::transformarEmDto)
        .collect(Collectors.toList());

    return new ResponseEntity<List<RachaResponseDTO>>(listDTO, HttpStatus.OK);
  }

  @Operation(summary = "Deleta um racha", description = "Deleta um racha com base no ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Racha deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Racha não encontrado"),
      @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para deletar o racha"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @DeleteMapping("/{idRacha}")
  public ResponseEntity deleteRachaByID(@PathVariable Long idRacha,
      @RequestHeader("rachadinha-login-token") String token) {
    try {
      UserSession userSession = sessionService.getSessionByToken(token);

      rachaService.deleteRachaById(idRacha, userSession.getUserId());
      return new ResponseEntity<String>("Racha deletado", HttpStatus.OK);

    } catch (ForbiddenUserException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Atualiza um racha", description = "Atualiza um racha com base nas informações passadas no corpo da requisição")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Racha atualizado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Racha não encontrado"),
      @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para atualizar o racha"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado")
  })
  @PatchMapping("/{idRacha}")
  public ResponseEntity updateRacha(@PathVariable Long idRacha, @RequestHeader("rachadinha-login-token") String token,
      @RequestBody RachaUpdateDTO rachaUpdateDTO) {
    try {

      UserSession userSession = sessionService.getSessionByToken(token);

      Racha racha = rachaService.findRachaById(idRacha).orElseThrow(() -> new RachaNotFoundException(idRacha));

      Racha rachaAtualizado = rachaService.updateRacha(rachaUpdateDTO, racha, userSession.getUserId());
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(rachaAtualizado);

      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.OK);

    } catch (ForbiddenUserException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
  }

  @Operation(summary = "Adiciona um usuário a um racha", description = "Adiciona um usuário a um racha com base nos IDs passados como parâmetro")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário adicionado ao racha com sucesso"),
      @ApiResponse(responseCode = "404", description = "Racha ou usuário não encontrado"),
      @ApiResponse(responseCode = "409", description = "Usuário já está no racha"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado"),
      @ApiResponse(responseCode = "400", description = "Senha incorreta para o racha"),
      @ApiResponse(responseCode = "403", description = "O racha está fechado ou encerrado")
  })
  @PostMapping("/join")
  public ResponseEntity joinRacha(@RequestHeader("rachadinha-login-token") String token, @RequestParam long idRacha,
      @RequestParam String pass) {
    try {

      UserSession userSession = sessionService.getSessionByToken(token);

      rachaService.addMemberToRacha(idRacha, userSession.getUserId(), pass);
      return new ResponseEntity<String>("Usuário adicionado ao racha", HttpStatus.OK);

    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserAlreadyInRachaException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
    } catch (IncorrectRachaPasswordException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    } catch (ClosedRachaException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }
  }

  @Operation(summary = "Remove um usuário de um racha", description = "Remove um usuário de um racha com base nos IDs passados como parâmetro")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário removido do racha com sucesso"),
      @ApiResponse(responseCode = "404", description = "Racha ou usuário não encontrado"),
      @ApiResponse(responseCode = "409", description = "Usuário não está no racha"),
      @ApiResponse(responseCode = "401", description = "Usuário não está logado"),
      @ApiResponse(responseCode = "403", description = "O racha está fechado ou encerrado")
  })
  @PostMapping("/leave")
  public ResponseEntity leaveRacha(@RequestHeader("rachadinha-login-token") String token, @RequestParam long idRacha) {
    try {

      UserSession userSession = sessionService.getSessionByToken(token);

      rachaService.removeMemberFromRacha(idRacha, userSession.getUserId());
      return new ResponseEntity<String>("Usuário removido do racha", HttpStatus.OK);

    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotInRachaException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
    } catch (UserNotLoggedInException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    } catch (ClosedRachaException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }
  }

  @Operation(summary = "Busca um racha pelo convite", description = "Retorna os dados de um racha com base no convite")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Racha encontrado"),
      @ApiResponse(responseCode = "403", description = "Racha não encontrado")
  })
  @GetMapping("/invite/{invite}")
  public ResponseEntity findByInvite(@PathVariable String invite) {
    try {
      Racha racha = rachaService.findRachaByInvite(invite);
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(racha);
      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.OK);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }
}
