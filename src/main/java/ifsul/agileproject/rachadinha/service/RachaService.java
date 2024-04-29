package ifsul.agileproject.rachadinha.service;

import java.util.List;

import ifsul.agileproject.rachadinha.domain.entity.Racha;

public interface RachaService {

    Racha saveRacha(Racha racha);

    void deleteRachabyId(Long id);

    Racha findRachaById(Long id);

    List<Racha> findAll();

    Racha updateRacha(Racha racha);
    
}