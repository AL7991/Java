package al.webapp.Controllers;

import al.webapp.Objects.UserTest;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class LoginApi {

    @PostMapping("/logIn")
    public String login(@RequestBody UserTest userTest){


        long time = System.currentTimeMillis();
        return  Jwts.builder()
                .setSubject(userTest.getUserName())
                .claim("roles","user")
                .setIssuedAt(new Date(time))
                .setExpiration(new Date(time + 60000))
                .signWith(SignatureAlgorithm.HS512, "ZG(9n.oY?s=]s,Q")
                .compact();

    }

    @GetMapping("/hello")
    public String smthng(){
        return "Hello";
    }


}
