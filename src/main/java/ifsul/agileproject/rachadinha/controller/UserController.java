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
    public User getUserByID(@PathVariable Integer id){
        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );
    }

    //Cadastrar user
    @PostMapping
    public ResponseEntity<UserRespostaDTO> saveUser(@RequestBody UserDTO dto){
      User usuario = userRepository.save(dto.transformaParaObjeto());
      return new ResponseEntity<>(UserRespostaDTO.transformaEmDTO(usuario), HttpStatus.CREATED);
    }

    //Deletar
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Integer id){
        userRepository.deleteById(id);
    }

    //Atualizar
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User usuario, @PathVariable Integer id){
        if(userRepository.existsById(id)){
            usuario.setId(id);
            return userRepository.save(usuario);
        }else{
            return new User();
        }
    }

    //Busca todos
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @PostMapping("/findByEmail")
    @ResponseStatus(HttpStatus.OK)
    public User findByEmail(@RequestBody User usuario){
        return userRepository.findByEmail(usuario.getEmail());
    }

    @PostMapping("/findByEmailAndPass")
    public User findByEmailAndPass(@RequestBody User usuario){
        return userRepository.findByEmailAndPassword(usuario.getEmail(), usuario.getPassword());
    }

}
