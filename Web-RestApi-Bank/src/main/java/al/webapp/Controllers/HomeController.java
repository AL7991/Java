package al.webapp.Controllers;

import al.webapp.Objects.Transaction;
import al.webapp.Objects.User;
import al.webapp.Other.checkBalance;
import al.webapp.Other.wrongAccountNumberException;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import al.webapp.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class HomeController {

    private UserRepository userRepo;
    private UserInfoRepository userInfoRepo;
    private AccountRepository accountRepo;
    private checkBalance checkBal;

    @Autowired
    public HomeController(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo, checkBalance checkBal) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
        this.checkBal = checkBal;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User>  getUserByUserName(@PathVariable("userName") String userName){

        Optional<User> optionalUser = userRepo.findByUserName(userName);

        if(optionalUser.isPresent()){
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>>  getAllUsers(){

        Optional<Iterable<User>> users = Optional.of(userRepo.findAll());

        if(users.isPresent()){
            return new ResponseEntity<>(users.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user){

        accountRepo.save(user.getAccount());

        userInfoRepo.save(user.getInfo());

        return userRepo.save(user);
    }

    @RequestMapping("/put")
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postTransaction(@RequestBody Transaction transaction){

        try {
            boolean b = checkBal.checkIfHaveCash(transaction.getAccountSender(), transaction.getAmount());

            if(b){
                try{
                    checkBal.doTransaction(transaction.getAccountSender(),transaction.getAccountReciver(), transaction.getAmount());
                } catch(wrongAccountNumberException e){
                    e.getMessage();
                }
            }

        } catch (wrongAccountNumberException e) {
            e.getMessage();
        }


    }

}
