package ifsul.agileproject.rachadinha.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import ifsul.agileproject.rachadinha.service.impl.RachaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RachaControllerTest {
    
    @InjectMocks
    RachaController controller;

    @Mock
    private RachaServiceImpl service;

    MockMvc mockMvc;


}
