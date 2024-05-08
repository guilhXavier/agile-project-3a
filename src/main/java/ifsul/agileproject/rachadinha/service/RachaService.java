package ifsul.agileproject.rachadinha.service;

import java.util.Optional;

import ifsul.agileproject.rachadinha.domain.dto.RachaDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;

public interface RachaService {
    
    Racha saveRacha(RachaRegisterDTO rachaDTO);

    Optional<Racha> findRachaById(Long id);

    void deleteRachaById(Long id);

    Racha updateRacha(RachaDTO rachaDTO);

    Racha findRachaByStatus(Status status);
    
}
