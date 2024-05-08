package ifsul.agileproject.rachadinha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;

@Transactional
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void testSalvarERecuperarUsuario() {
        User usuarioOriginal = new User();
        usuarioOriginal.setName("Teste");
        usuarioOriginal.setEmail("Teste@Teste");
        usuarioOriginal.setPassword("Teste123");

        User usuarioSalvo = userRepository.save(usuarioOriginal);

        User usuarioRecuperado = userRepository.findById(usuarioSalvo.getId()).get();

        assertEquals(usuarioSalvo, usuarioRecuperado);

    }

    @Test
    public void loginWithWrongPassword() {
        User user = new User();
        user.setName("Teste");
        user.setEmail("Teste@Teste");
        user.setPassword("Teste123");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("Teste@Teste", "Teste12345");

        assertEquals(null, userLogged);
    }

    @Test
    public void loginWithRightPasswordAndEmail() {
        User user = new User();
        user.setName("Teste");
        user.setEmail("Teste@Teste");
        user.setPassword("Teste123");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("Teste@Teste", "Teste123");

        assertEquals(user, userLogged);
    }

    @Test
    public void loginWithWrongEmail() {
        User user = new User();
        user.setName("Teste");
        user.setEmail("Teste@Teste");
        user.setPassword("Teste123");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("Teste2@Teste", "Teste123");

        assertEquals(null, userLogged);
    }

}
