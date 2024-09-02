package ifsul.agileproject.rachadinha.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import ifsul.agileproject.rachadinha.domain.dto.RachaDetailsDTO;
import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.mapper.RachaMapper;
import ifsul.agileproject.rachadinha.repository.PaymentRepository;
import ifsul.agileproject.rachadinha.repository.RachaRepository;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import ifsul.agileproject.rachadinha.service.UserService;
import ifsul.agileproject.rachadinha.service.impl.RachaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RachaServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private RachaRepository rachaRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private RachaMapper rachaMapper;

    @InjectMocks
    private RachaServiceImpl rachaService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@test");
        user.setPassword("pass");


        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetRachaOwner_Success() {

        Racha racha = mock(Racha.class);
        User owner = mock(User.class);
        when(rachaRepository.findById(1L)).thenReturn(Optional.of(racha));
        when(racha.getOwner()).thenReturn(owner);

        User result = rachaService.getRachaOwner(1L);

        assertEquals(owner, result);
        verify(rachaRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveRacha_UserNotFound() {
        RachaDetailsDTO rachaDTO = mock(RachaDetailsDTO.class);
        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> rachaService.saveRacha(rachaDTO, 1L));
    }

    @Test
    public void testSaveRacha_Success() {
        RachaDetailsDTO rachaDTO = mock(RachaDetailsDTO.class);
        User owner = mock(User.class);
        Racha racha = mock(Racha.class);
        Payment payment = mock(Payment.class);

        when(userRepository.existsById(1L)).thenReturn(true);
        when(userService.findUserById(1L)).thenReturn(Optional.of(owner));
        when(rachaMapper.toEntity(rachaDTO)).thenReturn(racha);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(rachaRepository.save(any(Racha.class))).thenReturn(racha);

        Racha result = rachaService.saveRacha(rachaDTO, 1L);

        assertEquals(racha, result);
        verify(userRepository, times(1)).existsById(1L);
        verify(userService, times(1)).findUserById(1L);
        verify(rachaMapper, times(1)).toEntity(rachaDTO);
        verify(rachaRepository, times(2)).save(racha);
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    public void testFindRachaById_RachaNotFound() {
        when(rachaRepository.existsById(1L)).thenReturn(false);

        assertThrows(RachaNotFoundException.class, () -> rachaService.findRachaById(1L));
    }

    @Test
    public void testFindRachaById_Success() {
        Racha racha = mock(Racha.class);
        when(rachaRepository.existsById(1L)).thenReturn(true);
        when(rachaRepository.findById(1L)).thenReturn(Optional.of(racha));

        Optional<Racha> result = rachaService.findRachaById(1L);

        assertTrue(result.isPresent());
        assertEquals(racha, result.get());
    }

    @Test
    public void testDeleteRachaById_ForbiddenUser() {
        Racha racha = mock(Racha.class);
        User owner = mock(User.class);
        when(rachaRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);
        when(rachaRepository.findById(1L)).thenReturn(Optional.of(racha));
        when(racha.getOwner()).thenReturn(owner);
        when(owner.getId()).thenReturn(1L);

        assertThrows(ForbiddenUserException.class, () -> rachaService.deleteRachaById(1L, 2L));
    }

    @Test
    public void testDeleteRachaById_Success() {
        Racha racha = mock(Racha.class);
        User owner = mock(User.class);
        when(rachaRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(rachaRepository.findById(1L)).thenReturn(Optional.of(racha));
        when(racha.getOwner()).thenReturn(owner);
        when(owner.getId()).thenReturn(1L);

        rachaService.deleteRachaById(1L, 1L);

        verify(rachaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAddMemberToRacha_UserAlreadyInRacha() {
        Racha racha = mock(Racha.class);
        User user = mock(User.class);
        Payment payment = mock(Payment.class);

        when(rachaRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);
        when(rachaRepository.findById(1L)).thenReturn(Optional.of(racha));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(paymentRepository.findByRachaAndUser(racha, user)).thenReturn(payment);

        assertThrows(NullPointerException.class, () -> rachaService.addMemberToRacha(1L, 2L, "password"));
    }

    @Test
    public void testAddMemberToRacha_Success() {
        Racha racha = mock(Racha.class);
        User user = mock(User.class);

        when(rachaRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(2L)).thenReturn(true);
        when(rachaRepository.findById(1L)).thenReturn(Optional.of(racha));
        when(racha.getStatus()).thenReturn(Status.OPEN);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(paymentRepository.findByRachaAndUser(racha, user)).thenReturn(null);

        rachaService.addMemberToRacha(1L, 2L, "password");

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddMemberToRacha_IncorrectPassword_ShouldThrowException() {
        Racha racha = mock(Racha.class);
        when(rachaRepository.findById(1L)).thenReturn(Optional.of(racha));
        when(userRepository.findById(1L)).thenReturn(Optional.of(mock(User.class)));
        when(racha.getStatus()).thenReturn(Status.OPEN);
        when(racha.getPassword()).thenReturn("correct_password");

        assertThrows(RachaNotFoundException.class, () -> {
            rachaService.addMemberToRacha(1L, 1L, "wrong_password");
        });

        verify(paymentRepository, never()).save(any(Payment.class));
    }

}