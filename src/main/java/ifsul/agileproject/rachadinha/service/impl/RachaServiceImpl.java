package ifsul.agileproject.rachadinha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ifsul.agileproject.rachadinha.repository.RachaRepository;
import ifsul.agileproject.rachadinha.repository.UserRepository;

@Service
public class RachaServiceImpl {

    @Autowired
    RachaRepository rachaRepository;

    @Autowired
    UserRepository userRepository;
    
    
}
