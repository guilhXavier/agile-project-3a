package ifsul.agileproject.rachadinha.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaResponseDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.service.impl.RachaServiceImpl;
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

  @Operation(summary = "Cria um racha", description = "Cria um racha com base nos dados passados no corpo da requisição")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Racha criado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  })
	@PostMapping("/create")
	public ResponseEntity createRacha(@RequestBody RachaRegisterDTO rachaDTO) {
    try {
      Racha racha = rachaService.saveRacha(rachaDTO);
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(racha);
      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.CREATED);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
	}

  @Operation(summary = "Lista todos os rachas criados por um usuário", description = "Retorna uma lista com todos os rachas criados por um usuário")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Rachas encontrados com sucesso"),
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
  })
	@GetMapping("/list/owner/{idOwner}")
	public ResponseEntity findByOwner(@PathVariable Long idOwner) {
    try {
      List<Racha> rachas = rachaService.findRachaByOwner(idOwner);
      List<RachaResponseDTO> rachasResponseDto = rachas.stream()
          .map(RachaResponseDTO::transformarEmDto)
          .collect(Collectors.toList());
      return new ResponseEntity<List<RachaResponseDTO>>(rachasResponseDto, HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
	}

  @Operation(summary = "Lista todos os rachas", description = "Retorna uma lista com todos os rachas")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Rachas encontrados com sucesso")
  })
  @GetMapping("/list/all")
  public ResponseEntity findAll(){
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
    @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para deletar o racha")
  })
	@DeleteMapping("/{idRacha}")
	public ResponseEntity deleteRachaByID(@PathVariable Long idRacha, @RequestParam Long idOwner) {
    try {
      rachaService.deleteRachaById(idRacha, idOwner);
      return new ResponseEntity<String>("Racha deletado", HttpStatus.OK);
    } catch (ForbiddenUserException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Atualiza um racha", description = "Atualiza um racha com base nas informações passadas no corpo da requisição")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Racha atualizado com sucesso"),
    @ApiResponse(responseCode = "404", description = "Racha não encontrado"),
    @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para atualizar o racha")
  })
  @PatchMapping("/{idRacha}/owner/{idOwner}")
  public ResponseEntity updateRacha(@PathVariable Long idRacha, @PathVariable Long idOwner, @RequestBody RachaUpdateDTO rachaUpdateDTO) {
    try {
      Racha racha = rachaService.findRachaById(idRacha).orElseThrow(() -> new RachaNotFoundException(idRacha));

      Racha rachaAtualizado = rachaService.updateRacha(rachaUpdateDTO, racha, idOwner);
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(rachaAtualizado);

      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.OK);
    } catch (ForbiddenUserException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }

  @Operation(summary = "Adiciona um usuário a um racha", description = "Adiciona um usuário a um racha com base nos IDs passados como parâmetro")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuário adicionado ao racha com sucesso"),
    @ApiResponse(responseCode = "404", description = "Racha ou usuário não encontrado"),
    @ApiResponse(responseCode = "409", description = "Usuário já está no racha"),
    @ApiResponse(responseCode = "401", description = "Senha incorreta")
  })
  @PostMapping("/join")
	public ResponseEntity joinRacha(@RequestParam long idUser, @RequestParam long idRacha, @RequestParam String pass) {
    try {
      rachaService.addMemberToRacha(idRacha, idUser, pass);
      return new ResponseEntity<String>("Usuário adicionado ao racha", HttpStatus.OK);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserAlreadyInRachaException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
    } catch (IncorrectRachaPasswordException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
	}

  @Operation(summary = "Remove um usuário de um racha", description = "Remove um usuário de um racha com base nos IDs passados como parâmetro")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Usuário removido do racha com sucesso"),
    @ApiResponse(responseCode = "404", description = "Racha ou usuário não encontrado"),
    @ApiResponse(responseCode = "409", description = "Usuário não está no racha")
  })
  @PostMapping("/leave")
  public ResponseEntity leaveRacha(@RequestParam long idUser, @RequestParam long idRacha) {
    try {
      rachaService.removeMemberFromRacha(idRacha, idUser);
      return new ResponseEntity<String>("Usuário removido do racha", HttpStatus.OK);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (UserNotInRachaException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.CONFLICT);
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

  @Operation(summary = "Busca os rachas que um usuário está participando", description = "Retorna a lista de rachas que o usuário está participando")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Lista encontrada"),
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
  })
  @GetMapping("/list/user/{userId}")
  public ResponseEntity getRachasByUserId(@PathVariable Long userId){
    try {
      List<Racha> listRachas = rachaService.getRachasByUserId(userId);

      List<RachaResponseDTO> listRachasDTO = listRachas.stream()
        .map(RachaResponseDTO::transformarEmDto)
        .toList();

      return new ResponseEntity(listRachasDTO, HttpStatus.OK);
    } catch (UserNotFoundException e){
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
  }
}
