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
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/racha")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class RachaController {

	private final RachaServiceImpl rachaService;

	@PostMapping("/criar")
	public ResponseEntity createRacha(@RequestBody RachaRegisterDTO rachaDTO) {
    try {
      Racha racha = rachaService.saveRacha(rachaDTO);
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(racha);
      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.CREATED);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
	}

	@GetMapping("/findByOwner/{id}")
	public ResponseEntity findByOwner(@PathVariable Long id) {
    try {
      List<Racha> rachas = rachaService.findRachaByOwner(id);
      List<RachaResponseDTO> rachasResponseDto = rachas.stream()
          .map(RachaResponseDTO::transformarEmDto)
          .collect(Collectors.toList());
      return new ResponseEntity<List<RachaResponseDTO>>(rachasResponseDto, HttpStatus.OK);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
	}

  @GetMapping("/findAll")
  public ResponseEntity findAll(){
    List<Racha> rachaList = rachaService.findAll();

    List<RachaResponseDTO> listDTO = rachaList.stream()
      .map(RachaResponseDTO::transformarEmDto)
      .collect(Collectors.toList());

    return new ResponseEntity<List<RachaResponseDTO>>(listDTO, HttpStatus.OK);
  }

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
}
