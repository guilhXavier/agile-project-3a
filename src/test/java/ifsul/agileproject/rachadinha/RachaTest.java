package ifsul.agileproject.rachadinha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.mapper.RachaMapper;
import ifsul.agileproject.rachadinha.repository.RachaRepository;

@Transactional
@SpringBootTest
public class RachaTest {
    
    @Autowired
    private RachaRepository rachaRepository;

    @Autowired
    private RachaMapper rachaMapper;

    @Test
    public void testSalvarERecuperarRacha(){        
        RachaRegisterDTO rachaRegister = new RachaRegisterDTO();
        rachaRegister.setName("Teste Nome");
        rachaRegister.setDescription("Teste Descrição");
        rachaRegister.setGoal(500.00);
        rachaRegister.setPassword("123");
        rachaRegister.setOwnerId(11L);

        Racha racha = rachaMapper.apply(rachaRegister);

        Racha rachaSalvo = rachaRepository.save(racha);

        Racha rachaRecuperado = rachaRepository.findById(rachaSalvo.getId()).get();

        assertEquals(rachaSalvo, rachaRecuperado);
    }

    @Test
    public void testFindRachaByInviteLink(){
        RachaRegisterDTO rachaRegister = new RachaRegisterDTO();
        rachaRegister.setName("Teste Nome");
        rachaRegister.setDescription("Teste Descrição");
        rachaRegister.setGoal(500.00);
        rachaRegister.setPassword("123");
        rachaRegister.setOwnerId(11L);

        Racha racha = rachaMapper.apply(rachaRegister);

        Racha rachaSalvo = rachaRepository.save(racha);

        Racha rachaRecuperado = rachaRepository.findByInviteLink(rachaSalvo.getInviteLink());

        assertEquals(rachaSalvo, rachaRecuperado);
    }
}
