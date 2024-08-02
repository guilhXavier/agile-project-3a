package ifsul.agileproject.rachadinha.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaResponseDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.ErrorResponse;
import ifsul.agileproject.rachadinha.exceptions.ForbiddenUserException;
import ifsul.agileproject.rachadinha.exceptions.RachaNotFoundException;
import ifsul.agileproject.rachadinha.exceptions.UserNotFoundException;
import ifsul.agileproject.rachadinha.service.impl.RachaServiceImpl;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/racha")
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class RachaController {

	private final RachaServiceImpl rachaService;
  private final UserServiceImpl userService;

	@PostMapping("/new")
	public ResponseEntity createRacha(@RequestBody RachaRegisterDTO rachaDTO) {
    try {
      Racha racha = rachaService.saveRacha(rachaDTO);
      RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(racha);
      return new ResponseEntity<RachaResponseDTO>(rachaResponseDTO, HttpStatus.CREATED);
    } catch (UserNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
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
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
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
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
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
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    } catch (RachaNotFoundException e) {
      return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  // TODO: move this to RachaService
  @PostMapping("/join")
	@Transactional
	public ResponseEntity joinRacha(@RequestParam long idUser, @RequestParam long idRacha, @RequestParam String pass) {
		Optional<User> userOpt = userService.findUserById(idUser);
		Optional<Racha> rachaOpt = rachaService.findRachaById(idRacha);

		if (rachaOpt.isPresent()) {
			if (userOpt.isPresent()) {
				User user = userOpt.get();
				Racha racha = rachaOpt.get();

				if (racha.getMembers().contains(user)) {
					return new ResponseEntity<>("Usuário já está no racha.", HttpStatus.OK);
				}

				if (racha.getPassword().equals(pass)) {
					racha.getMembers().add(user);
					user.getRachas().add(racha);
					rachaService.save(racha);
					userService.save(user);
					return new ResponseEntity<>("Usuário adicionado ao racha.", HttpStatus.OK);
				} else {
					return new ResponseEntity<>("Senha incorreta.", HttpStatus.BAD_REQUEST);
				}
			} else {
				return new ResponseEntity<>("Usuário não existe", HttpStatus.FORBIDDEN);
			}
		} else {
			return new ResponseEntity<>("Racha não existe", HttpStatus.FORBIDDEN);
		}
	}

  // TODO: move this to RachaService
  @PostMapping("/leave")
  @Transactional
  public ResponseEntity leaveRacha(@RequestParam long idUser, @RequestParam long idRacha) {

    Optional<User> userOpt = userService.findUserById(idUser);
    Optional<Racha> rachaOpt = rachaService.findRachaById(idRacha);

    if (rachaOpt.isPresent()) {
      if (userOpt.isPresent()) {
        User user = userOpt.get();
        Racha racha = rachaOpt.get();

        if(racha.getMembers().contains(user)){
          racha.getMembers().remove(user);
          return new ResponseEntity("Usuário removido do racha", HttpStatus.OK);
        } else{
          return new ResponseEntity("Usuário não está neste racha", HttpStatus.FORBIDDEN);
        }

      } else {
        return new ResponseEntity<>("Usuário não existe", HttpStatus.FORBIDDEN);
      }
    } else {
      return new ResponseEntity<>("Racha não existe", HttpStatus.FORBIDDEN);
    }
  }
}
