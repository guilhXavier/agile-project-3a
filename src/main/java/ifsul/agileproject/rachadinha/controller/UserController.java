package ifsul.agileproject.rachadinha.controller;

import ifsul.agileproject.rachadinha.domain.entity.User;
import ifsul.agileproject.rachadinha.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public User getUserByID(@PathVariable Integer id){
        return userService
                .findUserByID(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.")
                );
    }

    //Cadastrar user
    @PostMapping("/cadastro")
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User usuario){
        return userService.saveUser(usuario);
    }

    //Deletar
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByID(@PathVariable Integer id){
        userService.deleteUserByID(id);
    }

    //Atualizar
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserByID(@RequestBody User usuario, @PathVariable Integer id){
        if(userService.existsUserByID(id)){
            usuario.setId(id);
            return userService.saveUser(usuario);
        }else{
            return new User();
        }
    }

    //Busca todos
    @GetMapping("/findAll")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll(){
        return userService.findAll();
    }

    //Buscar usuário pelo email
    @PostMapping("/findByEmail")
    @ResponseStatus(HttpStatus.OK)
    public User findByEmail(@RequestBody User usuario){
        return userService.findUserByEmail(usuario.getEmail());
    }

    @PostMapping("/login")
    public User login(@RequestBody User usuario){
        return userService.login(usuario.getEmail(), usuario.getPassword());
    }

}
