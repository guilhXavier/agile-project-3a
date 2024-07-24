package ifsul.agileproject.rachadinha;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.repository.UserRepository;

@Transactional
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveAndRecoverUser() {
        User usuarioOriginal = new User();
        usuarioOriginal.setName("AccountTest");
        usuarioOriginal.setEmail("AccountTest");
        usuarioOriginal.setPassword("AccountTest");

        User usuarioSalvo = userRepository.save(usuarioOriginal);

        User usuarioRecuperado = userRepository.findById(usuarioSalvo.getId()).get();

        assertEquals(usuarioSalvo, usuarioRecuperado);

    }

    @Test
    public void loginWithWrongPassword() {
        User user = new User();
        user.setName("AccountTest");
        user.setEmail("AccountTest");
        user.setPassword("AccountTest");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("AccountTest", "Teste12345");

        assertEquals(null, userLogged);
    }

    @Test
    public void loginWithWrongEmail() {
        User user = new User();
        user.setName("AccountTest");
        user.setEmail("Teste4@Teste");
        user.setPassword("AccountTest");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("Teste5@Teste", "Teste123");

        assertEquals(null, userLogged);
    }

    @Test
    public void loginWithRightPasswordAndEmail() {
        User user = new User();
        user.setName("AccountTest");
        user.setEmail("AccountTest");
        user.setPassword("AccountTest");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("AccountTest", "AccountTest");

        assertEquals(user, userLogged);
    }

    @Test
    public void updateUserProperties() throws Exception{
        User user = new User();
        user.setName("AccountTest");
        user.setEmail("AccountTest");
        user.setPassword("AccountTest");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("AccountTest", "AccountTest");

        userLogged.setName("Teste2");
        userLogged.setEmail("Teste2@Teste");
        userLogged.setPassword("Teste1234");

        userRepository.save(userLogged);

        User userUpdated = userRepository.findByEmailAndPassword("Teste2@Teste", "Teste1234");

        assertEquals(userLogged, userUpdated);
    }

    @Test
    public void deleteUser() throws Exception{
        User user = new User();
        user.setName("AccountTest");
        user.setEmail("AccountTest");
        user.setPassword("AccountTest");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("AccountTest", "AccountTest");

        userRepository.delete(userLogged);

        User userDeleted = userRepository.findByEmailAndPassword("AccountTest", "AccountTest");

        assertEquals(null, userDeleted);
    }

    @Test
    public void deleteUserOwnAccount() throws Exception {
        User user = new User();
        user.setName("AccountTest");
        user.setEmail("AccountTest");
        user.setPassword("AccountTest");

        userRepository.save(user);

        User userLogged = userRepository.findByEmailAndPassword("AccountTest", "AccountTest");

        userRepository.delete(userLogged);

        User userDeleted = userRepository.findByEmailAndPassword("AccountTest", "AccountTest");

        assertEquals(null, userDeleted);
    }
    
}
