package al.webapp.controllers;

import al.webapp.objects.*;
import al.webapp.managers.UsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @DeleteMapping("/api/user/delete/{userName}")
    public ResponseEntity<String> removeUser(@PathVariable("userName") String userName){return usersManager.removeUser(userName); }
    @PutMapping("/updateUserInfo")
    public ResponseEntity<String> updateUserInfo(@Valid @RequestBody UserInfo info,Errors errors){return usersManager.changeUserInfo(info,errors); }

    @GetMapping("/loggedUser")
    public User getLoggedUser(){return usersManager.getLoggedUser(); }

    @RequestMapping("/register")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserRegister userRegister, Errors errors){return usersManager.saveUser(userRegister, errors); }

    @RequestMapping("/logIn")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> loginToken(@RequestBody UserLogIn userLogIn) {return usersManager.loginToken(userLogIn); }

}
