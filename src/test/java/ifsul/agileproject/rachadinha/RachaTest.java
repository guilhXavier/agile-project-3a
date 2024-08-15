package ifsul.agileproject.rachadinha;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ifsul.agileproject.rachadinha.domain.dto.RachaRegisterDTO;
import ifsul.agileproject.rachadinha.domain.dto.RachaUpdateDTO;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.mapper.RachaMapper;
import ifsul.agileproject.rachadinha.repository.RachaRepository;
import ifsul.agileproject.rachadinha.service.impl.RachaServiceImpl;

@Transactional
@SpringBootTest
public class RachaTest {
    
@Mock
    private RachaRepository rachaRepository;

    @Mock
    private RachaMapper rachaMapper;

    @InjectMocks
    private RachaServiceImpl rachaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveRacha() {
        User owner = new User();
        owner.setId(1L);
        
        RachaRegisterDTO rachaRegisterDTO = new RachaRegisterDTO();
        rachaRegisterDTO.setName("New Racha");
        rachaRegisterDTO.setPassword("password123");
        rachaRegisterDTO.setGoal(1000.0);
        rachaRegisterDTO.setOwnerId(1L);

        Racha racha = Racha.builder()
                .name(rachaRegisterDTO.getName())
                .password(rachaRegisterDTO.getPassword())
                .goal(rachaRegisterDTO.getGoal())
                .owner(owner)
                .build();

        when(rachaMapper.apply(rachaRegisterDTO)).thenReturn(racha);
        when(rachaRepository.save(racha)).thenReturn(racha);

        Racha savedRacha = rachaService.saveRacha(rachaRegisterDTO);

        assertNotNull(savedRacha);
        assertEquals("New Racha", savedRacha.getName());
        verify(rachaRepository, times(1)).save(racha);
    }

    @Test
    public void testFindRachaById() {
        Long rachaId = 1L;
        User owner = new User();
        owner.setId(1L);

        Racha racha = Racha.builder()
                .id(rachaId)
                .name("Existing Racha")
                .password("password123")
                .goal(1000.0)
                .owner(owner)
                .build();

        when(rachaRepository.findById(rachaId)).thenReturn(Optional.of(racha));

        Optional<Racha> foundRacha = rachaService.findRachaById(rachaId);

        assertTrue(foundRacha.isPresent());
        assertEquals("Existing Racha", foundRacha.get().getName());
        verify(rachaRepository, times(1)).findById(rachaId);
    }

    @Test
    public void testUpdateRacha() {
        Long rachaId = 1L;
        User owner = new User();
        owner.setId(1L);

        Racha existingRacha = Racha.builder()
                .id(rachaId)
                .name("Old Racha")
                .password("oldPassword")
                .goal(1000.0)
                .owner(owner)
                .build();

        RachaUpdateDTO rachaUpdateDTO = new RachaUpdateDTO();
        rachaUpdateDTO.setName("Updated Racha");
        rachaUpdateDTO.setGoal(1500.0);
        rachaUpdateDTO.setPassword("newPassword");

        when(rachaRepository.findById(rachaId)).thenReturn(Optional.of(existingRacha));
        when(rachaRepository.save(existingRacha)).thenReturn(existingRacha);

        Racha updatedRacha = rachaService.updateRacha(rachaUpdateDTO, existingRacha);

        assertNotNull(updatedRacha);
        assertEquals("Updated Racha", updatedRacha.getName());
        assertEquals(1500.0, updatedRacha.getGoal());
        assertEquals("newPassword", updatedRacha.getPassword());
        verify(rachaRepository, times(1)).save(existingRacha);
    }

    @Test
    public void testDeleteRachaById() {
        Long rachaId = 1L;

        doNothing().when(rachaRepository).deleteById(rachaId);

        rachaService.deleteRachaById(rachaId);

        verify(rachaRepository, times(1)).deleteById(rachaId);
    }

    @Test
    public void testFindRachaByStatus() {
        User owner = new User();
        owner.setId(1L);

        Racha racha = Racha.builder()
                .name("Open Racha")
                .password("password123")
                .goal(1000.0)
                .owner(owner)
                .status(Status.OPEN)
                .build();

        when(rachaRepository.findByStatus(Status.OPEN)).thenReturn(racha);

        Racha foundRacha = rachaService.findRachaByStatus(Status.OPEN);

        assertNotNull(foundRacha);
        assertEquals(Status.OPEN, foundRacha.getStatus());
        assertEquals("Open Racha", foundRacha.getName());
        verify(rachaRepository, times(1)).findByStatus(Status.OPEN);
    }
    
    @Test
    public void testRachaStatusUpdate() {
        User owner = new User();
        owner.setId(1L);
        owner.setName("Owner Name");

        Racha racha = Racha.builder()
                .name("Racha Test")
                .password("password123")
                .goal(1000.0)
                .owner(owner)
                .status(Status.OPEN)
                .build();

        racha.setStatus(Status.CLOSED);
        assertEquals(Status.CLOSED, racha.getStatus());
    }
}
