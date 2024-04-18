package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserRespostaDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Buscar user por ID
    @GetMapping("{id}")
    public ResponseEntity<UserRespostaDTO> getUserByID(@PathVariable Integer id){
      User usuario = userService.findUserByID(id);
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario), HttpStatus.OK);
    }

    //Cadastrar user
    @PostMapping("/cadastro")
    public ResponseEntity<UserRespostaDTO> saveUser(@RequestBody UserDTO dto){
      User usuario = userService.saveUser(dto.transformaParaObjeto());
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario), HttpStatus.CREATED);
    }

    //Deletar usuário pelo ID
    @DeleteMapping("{id}")
    public ResponseEntity deleteUserByID(@PathVariable Integer id){
      userService.deleteUserByID(id);
      return new ResponseEntity("Usuário deletado", HttpStatus.OK);
    }

    //Atualizar usuário pelo ID
    @PatchMapping("{id}")
    public ResponseEntity updateUser(@RequestBody UserDTO dto, @PathVariable Integer id){
      if(userService.existsUserByID(id)){
        User user = userService.findUserByID(id);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(user), HttpStatus.CREATED);
      }else{
        return new ResponseEntity<>("Usuário não encontrado", HttpStatus.OK);
      }
    }

    //Busca todos usuários
    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
      List<User> userList = userService.findAll();
      return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    //Buscar usuário pelo EMAIL
    @PostMapping("/findByEmail")
    public ResponseEntity<UserRespostaDTO> findByEmail(@RequestBody UserDTO dto){
      User user = userService.findUserByEmail(dto.getEmail());
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(user), HttpStatus.OK);
    }

    //Login do usuário com EMAIL e PASSWORD
    @PostMapping("/login")
    public ResponseEntity<UserRespostaDTO> login(@RequestBody UserDTO dto){
      User user = userService.login(dto.getEmail(), dto.getPassword());
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(user), HttpStatus.OK);
    }

}
