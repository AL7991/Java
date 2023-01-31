package al.webapp.Controllers;

import al.webapp.Objects.Transaction;
import al.webapp.Objects.User;
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

    @GetMapping("/{userName}")
    public ResponseEntity<User>  getUserByUserName(@PathVariable("userName") String userName){
        return manager.getUserByUserName(userName);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>>  getAllUsers(){
        return manager.getAllUsers();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user){
        return manager.saveUser(user);
    }

    @RequestMapping("/transfer")
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postTransaction(@RequestBody Transaction transaction){
        manager.saveTransaction(transaction);
    }

}
