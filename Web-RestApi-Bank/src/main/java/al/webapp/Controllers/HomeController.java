package al.webapp.Controllers;

import al.webapp.Objects.User;
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

    @Autowired
    public HomeController(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User>  getUserByUserName(@PathVariable("userName") String userName){
        Optional<User> optionalUser = userRepo.findByUserName(userName);

        if(optionalUser.isPresent()){
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
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



}
