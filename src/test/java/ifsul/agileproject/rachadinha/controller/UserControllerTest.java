package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserLoginDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserResponseDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.UserNotFoundException;
import ifsul.agileproject.rachadinha.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUserByID() {
        // Arrange
        User user = new User();
        user.setId((long) 55);
        user.setName("DiegoTeste");
        user.setEmail("DiegoTeste");
        user.setPassword("DiegoTeste");
        user.setRachas(new ArrayList<>());
        // Mocking findUserById to return the user
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user));

        // Act: Fetch the user by ID
        ResponseEntity<UserResponseDTO> response = userController.getUserByID((long) 55);

        // Assert: Verify fetch response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("DiegoTeste", response.getBody().getName());
        assertEquals("DiegoTeste", response.getBody().getEmail());
    }

    @Test
    public void testFindAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/user/findAll"))
                .andExpect(status().isOk());
        
        ResponseEntity<List<UserResponseDTO>> response =  userController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(6, response.getBody().size());
    }
   
    
}
