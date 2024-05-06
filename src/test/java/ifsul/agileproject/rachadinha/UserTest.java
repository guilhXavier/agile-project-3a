package ifsul.agileproject.rachadinha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.repository.UserRepository;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;
    
    @Test
    void contextLoads() {
    }

    @Test
    @Transactional 
    public void testSalvarERecuperarUsuario() {

        User usuarioOriginal = new User();
        usuarioOriginal.setName("Diego");
        usuarioOriginal.setEmail("Diego@Test");
        usuarioOriginal.setPassword("test");

        User usuarioSalvo = userRepository.save(usuarioOriginal);

        User usuarioRecuperado = userRepository.findById(usuarioSalvo.getId()).get();

        assertEquals(usuarioRecuperado.getId(), usuarioOriginal.getId());
        assertEquals(usuarioRecuperado.getName(), usuarioOriginal.getName());
        assertEquals(usuarioRecuperado.getEmail(), usuarioOriginal.getEmail());
        assertEquals(usuarioRecuperado.getPassword(), usuarioOriginal.getPassword());

       }
}
