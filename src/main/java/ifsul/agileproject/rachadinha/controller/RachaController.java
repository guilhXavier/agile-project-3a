package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.RachaResponseDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.service.impl.RachaServiceImpl;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/racha")
@AllArgsConstructor
public class RachaController {

	private final RachaServiceImpl rachaService;
  private final UserServiceImpl userService;

	// Criar racha
	@PostMapping("/criar")
	public ResponseEntity<RachaResponseDTO> createRacha(@RequestBody RachaRegisterDTO rachaDTO) {
		Racha racha = rachaService.saveRacha(rachaDTO);

		RachaResponseDTO rachaResponseDTO = RachaResponseDTO.transformarEmDto(racha);

		return new ResponseEntity<>(rachaResponseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/findByOwner/{id}")
	public ResponseEntity<List<RachaResponseDTO>> findByOwner(@PathVariable Long id) {
		User owner = new User();
		owner.setId(id);

		List<Racha> rachas = rachaService.findRachaByOwner(owner);

		List<RachaResponseDTO> rachasResponseDto = rachas.stream()
				.map(RachaResponseDTO::transformarEmDto)
				.collect(Collectors.toList());

		return new ResponseEntity<>(rachasResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/{idRacha}")
	public ResponseEntity deleteRachaByID(@PathVariable Long idRacha, @RequestParam Long idOwner) {

    Optional<Racha> racha = rachaService.findRachaById(idRacha);
    Optional<User> owner = userService.findUserById(idOwner);

    if (racha.isPresent()) {
      if(owner.isPresent()) {

        if (racha.get().getOwner().getId() != owner.get().getId()) {
          return new ResponseEntity("Usuário não autorizado", HttpStatus.FORBIDDEN);
        }

        rachaService.deleteRachaById(racha.get().getId());
        return new ResponseEntity("Racha deletado", HttpStatus.OK);
      } else{
        return new ResponseEntity("Usuário não existe", HttpStatus.FORBIDDEN);
      }
    } else {
      return new ResponseEntity("Racha não existe", HttpStatus.FORBIDDEN);
    }
  }

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
