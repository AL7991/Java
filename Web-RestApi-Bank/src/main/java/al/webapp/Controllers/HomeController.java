package al.webapp.Controllers;

import al.webapp.Objects.Transaction;
import al.webapp.Objects.User;
import al.webapp.Objects.UserLogIn;
import al.webapp.Objects.UserRegister;
import al.webapp.Other.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HomeController {

    private Manager manager;

    @Autowired
    public HomeController(Manager manager) {
        this.manager = manager;
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<User>  getUserByUserName(@PathVariable("userName") String userName){
        return manager.getUserByUserName(userName);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>>  getAllUsers(){
        return manager.getAllUsers();
    }

    @RequestMapping("/register")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserRegister userRegister){
        return manager.saveUser(userRegister);
    }

    @RequestMapping("/transfer")
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postTransaction(@RequestBody Transaction transaction){
        manager.saveTransaction(transaction);
    }

    @PostMapping("/logIn")
    public ResponseEntity<String> loginToken(@RequestBody UserLogIn userLogIn) {

        return manager.login(userLogIn);

    }
    @GetMapping("/test")
    public String test() {
        return "hello user";
    }

    @GetMapping("/test2")
    public String test2() {
        return "hello admin";
    }

}
