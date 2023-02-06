package al.webapp.Controllers;

import al.webapp.Objects.User;
import al.webapp.Objects.UserRegister;
import al.webapp.Objects.UserLogIn;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
public class LoginApi {

    private UserRepository userRepo;
    private UserInfoRepository userInfoRepo;
    private AccountRepository accountRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public LoginApi(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/logIn")
    public ResponseEntity<String> login(@RequestBody UserLogIn userTest) {

        Optional<User> user = userRepo.findByUserName(userTest.getUserName());

        if (user.isPresent()) {

            if (passwordEncoder.matches(userTest.getPassword(), user.get().getPassword())) {

                long time = System.currentTimeMillis();

                String token = Jwts.builder()
                        .claim("name", userTest.getUserName())
                        .claim("role", user.get().getRole())
                        .setIssuedAt(new Date(time))
                        .setExpiration(new Date(time + 3600000))
                        .signWith(SignatureAlgorithm.HS512, "ZG(9n.oY?s=]s,Q")
                        .compact();

                return new ResponseEntity<>(token, HttpStatus.OK);

            }

        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegister userRegister){

        Optional<User> user = userRepo.findByUserName(userRegister.getUserName());

        if (user.isPresent()) {
            System.out.println("User name is in used , change user name.");

            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }else{

            userRegister.userRegisterToUser(passwordEncoder,accountRepo,userInfoRepo,userRepo);

            return new ResponseEntity<>(null, HttpStatus.CREATED);

        }

    }


    @GetMapping("/hello")
    public String test() {
        return "Hello";
    }

    @GetMapping("/hello2")
    public String test2() {
        return "Hello2";
    }




}
