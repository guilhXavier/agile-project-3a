package ifsul.agileproject.rachadinha.service;

import ifsul.agileproject.rachadinha.domain.dto.UserDetailsDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.*;
import ifsul.agileproject.rachadinha.mapper.UserMapper;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDetailsDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Test");
        user.setEmail("test@test");
        user.setPassword("pass");

        userDTO = new UserDetailsDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("test@test");
        userDTO.setPassword("pass");
    }

    @Test
    void saveUser_ShouldSaveUser_WhenEmailNotUsed() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(userDTO);

        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveUser_ShouldThrowException_WhenEmailAlreadyUsed() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyUsedException.class, () -> userService.saveUser(userDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findUserById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getId(), foundUser.get().getId());
    }

    @Test
    void findUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.existsById(user.getId())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(user.getId()));
    }

    @Test
    void deleteUserById_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.existsById(user.getId())).thenReturn(true);

        userService.deleteUserById(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    void deleteUserById_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.existsById(user.getId())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(user.getId()));
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsAreCorrect() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        when(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(user);

        User loggedUser = userService.login(user.getEmail(), user.getPassword());

        assertNotNull(loggedUser);
        assertEquals(user.getEmail(), loggedUser.getEmail());
    }

    @Test
    void login_ShouldThrowException_WhenEmailDoesNotExist() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.login(user.getEmail(), user.getPassword()));
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsIncorrect() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);
        when(userRepository.findByEmailAndPassword(user.getEmail(), "wrong pass")).thenReturn(null);

        assertThrows(IncorrectUserPasswordException.class, () -> userService.login(user.getEmail(), "wrong pass"));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.updateUser(userDTO);

        assertNotNull(updatedUser);
        assertEquals(userDTO.getName(), updatedUser.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void resetPassword_ShouldUpdatePassword_WhenOriginalPasswordIsCorrect() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = userService.resetPassword(user.getId(), user.getPassword(), "new pass");

        assertNotNull(updatedUser);
        assertEquals("new pass", updatedUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void resetPassword_ShouldThrowException_WhenOriginalPasswordIsIncorrect() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(IncorrectUserPasswordException.class,
                () -> userService.resetPassword(user.getId(), "wrong pass", "new pass"));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void resetPassword_ShouldThrowException_WhenUserDoesNotExist() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.resetPassword(user.getId(), user.getPassword(), "new pass"));
    }

    @Test
    void findAllUsers_ShouldReturnAllUsers() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setName("Test2");
        anotherUser.setEmail("Test2@test");
        anotherUser.setPassword("pass");

        when(userRepository.findAll()).thenReturn(List.of(user, anotherUser));

        List<User> users = userService.findAll();

        assertEquals(2, users.size());
        assertEquals("Test", users.get(0).getName());
        assertEquals("Test2", users.get(1).getName());
    }

    @Test
    void findUserByEmail_ShouldReturnUser_WhenEmailExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExistsAndLoggedUserIsOwner() {
        Long loggedUserId = 1L;

        User existingUser = new User();
        existingUser.setId(loggedUserId);
        existingUser.setEmail("oldemail@test");

        UserDetailsDTO userDTO = new UserDetailsDTO();
        userDTO.setEmail("newemail@test");
        userDTO.setName("Name Changed");
        userDTO.setPassword("newpassword");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser(userDTO, loggedUserId);

        assertEquals(userDTO.getEmail(), updatedUser.getEmail());
        assertEquals(userDTO.getName(), updatedUser.getName());
        assertEquals(userDTO.getPassword(), updatedUser.getPassword());

        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUser_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        Long loggedUserId = 1L;

        UserDetailsDTO userDTO = new UserDetailsDTO();
        userDTO.setEmail("nonexistent@test");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO, loggedUserId));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void updateUser_ShouldThrowForbiddenUserException_WhenLoggedUserIsNotOwner() {

        Long loggedUserId = 1L;

        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setEmail("oldemail@test");

        UserDetailsDTO userDTO = new UserDetailsDTO();
        userDTO.setEmail("oldemail@test");

        when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(existingUser));

        assertThrows(ForbiddenUserException.class, () -> userService.updateUser(userDTO, loggedUserId));

        verify(userRepository, never()).save(any(User.class));
    }

}