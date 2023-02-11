package al.webapp.Controllers;

import al.webapp.Objects.Transaction;
import al.webapp.Objects.User;
import al.webapp.Objects.UserLogIn;
import al.webapp.Objects.UserRegister;
import al.webapp.Other.Manager;
import al.webapp.Other.checkCredit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class HomeController {

    private Manager manager;
    private checkCredit checkCredit;

    @Autowired
    public HomeController(Manager manager, checkCredit checkCredit) {

        this.manager = manager;
        this.checkCredit = checkCredit;
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

    @PostMapping("/takeCredit")
    public ResponseEntity<String> takeCredit(@RequestBody BigDecimal amount) {

        return checkCredit.takeCredit(amount);

    }

    @PostMapping("/repaymentPartOfCredit")
    public ResponseEntity<String> repaymentPartOfCredit(@RequestBody BigDecimal amount) {
            return checkCredit.repaymentPartOfCredit(amount);
    }

    @PostMapping("/repaymentAllOfCredit")
    public ResponseEntity<String> repaymentAllOfCredit() {
        return checkCredit.repaymentAllOfCredit();
    }


}
