package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.dto.UserDTO;
import ifsul.agileproject.rachadinha.domain.dto.UserRespostaDTO;
import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.exceptions.UserNotFoundException;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
      this.userRepository = userRepository;
    }

    //Buscar user por ID
    @GetMapping("{id}")
    public ResponseEntity<UserRespostaDTO> getUserByID(@PathVariable Integer id){
      User usuario = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario), HttpStatus.OK);
    }

    //Cadastrar user
    @PostMapping
    public ResponseEntity<UserRespostaDTO> saveUser(@RequestBody UserDTO dto){
      User usuario = userRepository.save(dto.transformaParaObjeto());
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario), HttpStatus.CREATED);
    }

    //Deletar user pelo id
    @DeleteMapping("{id}")
    public ResponseEntity deleteUserById(@PathVariable Integer id){
      userRepository.deleteById(id);
      return new ResponseEntity("Usuário deletado", HttpStatus.OK);
    }

    //Atualizar user pelo id
    @PutMapping("{id}")
    public ResponseEntity<UserRespostaDTO> updateUser(@RequestBody User usuario, @PathVariable Integer id){
        if(userRepository.existsById(id)){
            usuario.setId(id);
            userRepository.save(usuario);
            return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Busca todos
    @GetMapping("/findAll")
    public ResponseEntity<List<User>> findAll(){
        List<User> userList = userRepository.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<UserRespostaDTO> findByEmail(@RequestBody User usuario){
        User user = userRepository.findByEmail(usuario.getEmail());
        return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserRespostaDTO> login(@RequestBody User usuario){
        User user = userRepository.findByEmailAndPassword(usuario.getEmail(), usuario.getPassword());
        return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(user), HttpStatus.OK);
    }

}
