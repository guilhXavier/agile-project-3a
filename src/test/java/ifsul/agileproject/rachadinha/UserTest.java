package ifsul.agileproject.rachadinha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.repository.UserRepository;

@Transactional
@SpringBootTest
@AllArgsConstructor
public class UserTest {

    private UserRepository userRepository;

    @Test
    public void testSalvarERecuperarUsuario() {
        User usuarioOriginal = new User();
        usuarioOriginal.setName("Teste");
        usuarioOriginal.setEmail("Teste1@Teste");
        usuarioOriginal.setPassword("Teste123");

        User usuarioSalvo = userRepository.save(usuarioOriginal);

        User usuarioRecuperado = userRepository.findById(usuarioSalvo.getId()).get();

        assertEquals(usuarioSalvo, usuarioRecuperado);

    }

    @Test
    public void loginWithWrongPassword() {
        User user = new User();
        user.setName("Teste");
        user.setEmail("Teste2@Teste");
        user.setPassword("Teste123");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("Teste2@Teste", "Teste12345");

        assertEquals(null, userLogged);
    }

    @Test
    public void loginWithRightPasswordAndEmail() {
        User user = new User();
        user.setName("Teste");
        user.setEmail("Teste3@Teste");
        user.setPassword("Teste123");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("Teste3@Teste", "Teste123");

        assertEquals(user, userLogged);
    }

    @Test
    public void loginWithWrongEmail() {
        User user = new User();
        user.setName("Teste");
        user.setEmail("Teste4@Teste");
        user.setPassword("Teste123");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("Teste5@Teste", "Teste123");

        assertEquals(null, userLogged);
    }

}
