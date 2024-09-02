package ifsul.agileproject.rachadinha.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ifsul.agileproject.rachadinha.domain.entity.Status;
import ifsul.agileproject.rachadinha.domain.entity.Payment;
import ifsul.agileproject.rachadinha.domain.entity.Racha;
import ifsul.agileproject.rachadinha.domain.entity.User;

import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class RachaTest {

    private Racha racha;
    private User owner;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        owner = new User(1L, "Test", "Test@test", "pass", null);
        
        payment1 = Payment.builder()
                .id(1L)
                .user(owner)
                .isOwner(true)
                .build();

        payment2 = Payment.builder()
                .id(2L)
                .user(new User(2L, "Test2", "Test2@Test", "pass", null))
                .isOwner(false)
                .build();

        racha = Racha.builder()
                .name("Racha 1")
                .password("pass")
                .goal(1000.0)
                .balance(500.0)
                .portionPerMember(250.0)
                .members(new ArrayList<>())
                .created_at(new Date())
                .build();

        racha.getMembers().add(payment1);
        racha.getMembers().add(payment2);
    }

    @Test
    void testGetOwner() {
        assertEquals(owner, racha.getOwner());
    }


    @Test
    void testGetName() {
        assertEquals("Racha 1", racha.getName());
    }

    @Test
    void testSetName() {
        racha.setName("Racha 2");
        assertEquals("Racha 2", racha.getName());
    }

    @Test
    void testGetPassword() {
        assertEquals("pass", racha.getPassword());
    }

    @Test
    void testSetPassword() {
        racha.setPassword("new pass");
        assertEquals("new pass", racha.getPassword());
    }

    @Test
    void testGetGoal() {
        assertEquals(1000.0, racha.getGoal(), 0.001);
    }

    @Test
    void testSetGoal() {
        racha.setGoal(2000.0);
        assertEquals(2000.0, racha.getGoal(), 0.001);
    }

    @Test
    void testGetBalance() {
        assertEquals(500.0, racha.getBalance(), 0.001);
    }

    @Test
    void testSetBalance() {
        racha.setBalance(1500.0);
        assertEquals(1500.0, racha.getBalance(), 0.001);
    }

    @Test
    void testGetPortionPerMember() {
        assertEquals(250.0, racha.getPortionPerMember(), 0.001);
    }

    @Test
    void testSetPortionPerMember() {
        racha.setPortionPerMember(300.0);
        assertEquals(300.0, racha.getPortionPerMember(), 0.001);
    }

    @Test
    void testGetStatus() {
        assertEquals(Status.OPEN, racha.getStatus());
    }

    @Test
    void testSetStatus() {
        racha.setStatus(Status.CLOSED);
        assertEquals(Status.CLOSED, racha.getStatus());
    }
}
