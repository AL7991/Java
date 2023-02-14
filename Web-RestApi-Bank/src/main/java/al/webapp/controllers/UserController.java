package al.webapp.controllers;

import al.webapp.objects.*;
import al.webapp.managers.UsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private UsersManager usersManager;

    @Autowired
    public UserController(UsersManager usersManager) {
        this.usersManager = usersManager;
    }

    @GetMapping("/api/user/{userName}")
    public ResponseEntity<User>  getUserByUserName(@PathVariable("userName") String userName){return usersManager.getUserByUserName(userName); }

    @GetMapping("/api/user/all")
    public ResponseEntity<Iterable<User>>  getAllUsers(){
        return usersManager.getAllUsers();
    }

    @RequestMapping("/register")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserRegister userRegister){return usersManager.saveUser(userRegister); }

    @RequestMapping("/logIn")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> loginToken(@RequestBody UserLogIn userLogIn) {return usersManager.loginToken(userLogIn); }


}
