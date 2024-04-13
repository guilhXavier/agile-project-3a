package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.repository.UserRepository;
import org.springframework.http.HttpStatus;
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
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
                );
    }

    //Cadastrar user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveCliente(@RequestBody User usuario){
        return userRepository.save(usuario);
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
