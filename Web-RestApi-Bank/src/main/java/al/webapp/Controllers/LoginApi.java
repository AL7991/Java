package al.webapp.Controllers;

import al.webapp.Objects.User;
import al.webapp.Objects.UserTest;
import al.webapp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
public class LoginApi {

    private UserRepository userRepo;

    @Autowired
    public LoginApi(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/logIn")
    public ResponseEntity<String> login(@RequestBody UserTest userTest){

        Optional<User> user = userRepo.findByUserName(userTest.getUserName());

        if(user.isPresent()){

            if(user.get().getPassword().equals(userTest.getPassword())){

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

    @GetMapping("/hello")
    public String smthng(){
        return "Hello";
    }

    @GetMapping("/hello2")
    public String smthng2(){
        return "Hello2";
    }


}
