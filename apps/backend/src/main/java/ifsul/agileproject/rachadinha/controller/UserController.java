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
        /*
         * Verificar se já há um usuário com esse id
        if (!userRepository.existsById(usuario.getId())) {
            return userRepository.save(usuario);
        } else {
            return ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
        */

        return userRepository.save(usuario);
    }

    //Deletar
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Integer id){
        userRepository.deleteById(id);
    }

    //Atualizar
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody User usuario){
        return userRepository.save(usuario);
    }

    //Busca todos
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll(){
        return userRepository.findAll();
    }

}
