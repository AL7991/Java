package al.webapp.controllers;

import al.webapp.objects.Transfer;
import al.webapp.objects.User;
import al.webapp.objects.UserLogIn;
import al.webapp.objects.UserRegister;
import al.webapp.managers.UsersManager;
import al.webapp.managers.CreditManager;
import al.webapp.managers.TransactionsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class HomeController {

    private UsersManager usersManager;
    private CreditManager creditManager;
    private TransactionsManager transactionsManager;

    @Autowired
    public HomeController(UsersManager usersManager, CreditManager creditManager, TransactionsManager transactionsManager) {

        this.usersManager = usersManager;
        this.creditManager = creditManager;
        this.transactionsManager = transactionsManager;
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<User>  getUserByUserName(@PathVariable("userName") String userName){return usersManager.getUserByUserName(userName); }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>>  getAllUsers(){
        return usersManager.getAllUsers();
    }

    @RequestMapping("/register")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> addUser(@RequestBody UserRegister userRegister){return usersManager.saveUser(userRegister); }

    @RequestMapping("/transfer")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> doTransfer(@RequestBody Transfer transfer){return transactionsManager.doTransfer(transfer); }

    @RequestMapping("/logIn")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> loginToken(@RequestBody UserLogIn userLogIn) {return usersManager.loginToken(userLogIn); }

    @RequestMapping("/takeCredit")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> takeCredit(@RequestBody BigDecimal amount) {return creditManager.takeCredit(amount); }

    @RequestMapping("/repaymentPartOfCredit")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> repaymentPartOfCredit(@RequestBody BigDecimal amount) {return creditManager.repaymentPartOfCredit(amount); }

    @PostMapping("/repaymentAllOfCredit")
    public ResponseEntity<String> repaymentAllOfCredit() {return creditManager.repaymentAllOfCredit(); }

}
