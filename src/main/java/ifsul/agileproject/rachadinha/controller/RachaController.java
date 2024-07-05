package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.RachaResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ifsul.agileproject.rachadinha.domain.dto.RachaDTO;
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

	
	@DeleteMapping("/{idRacha}/owner/{idOwner}")

	public ResponseEntity deleteRachaByID(@PathVariable Long idRacha, @PathVariable Long idOwner) {
	
		Racha racha = rachaService.findRachaById(idRacha).get();

	if (racha.getOwner().getId() != idOwner){
		return new ResponseEntity("Usuário não autorizado", HttpStatus.FORBIDDEN);
	}

     rachaService.deleteRachaById(racha.getId());
        return new ResponseEntity("Racha deletado", HttpStatus.OK);
	
    }
}

