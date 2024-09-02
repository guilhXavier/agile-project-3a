package ifsul.agileproject.rachadinha.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ifsul.agileproject.rachadinha.domain.entity.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("User")
                .email("User@User")
                .password("123")
                .build();
    }

    @Test
    void testGetId() {
        assertEquals(1L, user.getId());
    }

    @Test
    void testGetName() {
        assertEquals("User", user.getName());
    }

    @Test
    void testSetName() {
        user.setName("User2");
        assertEquals("User2", user.getName());
    }

    @Test
    void testGetEmail() {
        assertEquals("User@User", user.getEmail());
    }

    @Test
    void testSetEmail() {
        user.setEmail("User2@User2");
        assertEquals("User2@User2", user.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals("123", user.getPassword());
    }

    @Test
    void testSetPassword() {
        user.setPassword("1234567");
        assertEquals("1234567", user.getPassword());
    }
}
