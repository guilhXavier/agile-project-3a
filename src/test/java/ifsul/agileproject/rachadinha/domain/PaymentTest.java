package ifsul.agileproject.rachadinha.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentTest {

    private Payment payment;
    private Racha racha;
    private User user;

    @BeforeEach
    void setUp() {
        racha = new Racha();
        user = new User();
        payment = Payment.builder()
                .racha(racha)
                .user(user)
                .userSaidPaid(true)
                .confirmedByOwner(false)
                .isOwner(false)
                .build();
    }

    @Test
    void testHasUserSaidPaid() {
        assertTrue(payment.hasUserSaidPaid(), "O método hasUserSaidPaid() deve retornar true.");
    }

    @Test
    void testSetUserSaidPaid() {
        payment.setUserSaidPaid(false);
        assertFalse(payment.hasUserSaidPaid(), "O método hasUserSaidPaid() deve retornar false após setUserSaidPaid(false).");
    }

    @Test
    void testSetConfirmedByOwner() {
        payment.setConfirmedByOwner(true);
        assertTrue(payment.isConfirmedByOwner(), "O método isConfirmedByOwner() deve retornar true após setConfirmedByOwner(true).");
    }

    @Test
    void testSetIsOwner() {
        payment.setOwner(true);
        assertTrue(payment.isOwner(), "O método isOwner() deve retornar true após setOwner(true).");
    }

    @Test
    void testGetUser() {
        assertEquals(user, payment.getUser(), "O método getUser() deve retornar o usuário correto.");
    }

    @Test
    void testGetRacha() {
        assertEquals(racha, payment.getRacha(), "O método getRacha() deve retornar o racha correto.");
    }
}
