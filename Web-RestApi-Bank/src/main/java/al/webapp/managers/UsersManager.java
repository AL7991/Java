package al.webapp.managers;

import al.webapp.objects.*;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManager {
    private UserRepository userRepo;
    private UserInfoRepository userInfoRepo;
    private AccountRepository accountRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersManager(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<User> getUserByUserName(String userName){

        Optional<User> optionalUser = userRepo.findByUserName(userName);

        if(optionalUser.isPresent()){
            return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Iterable<User>>  getAllUsers(){

        Optional<Iterable<User>> users = Optional.of(userRepo.findAll());

        if(users.isPresent()){
            return new ResponseEntity<>(users.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> removeUser(String userName){
        Optional<User> userToRemove = userRepo.findByUserName(userName);
        if(userToRemove.isPresent()){
            userRepo.delete(userToRemove.get());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> saveUser(UserRegister userRegister, Errors errors){

        Optional<User> user = userRepo.findByUserName(userRegister.getUserName());

        List listOfErrors = errorsToList(errors);

        if(listOfErrors.size() > 0) {
            return new ResponseEntity<>(listOfErrors.toString(), HttpStatus.NOT_ACCEPTABLE);
        }

        if (user.isPresent()) {
            return new ResponseEntity<>("User name is in used , change user name.", HttpStatus.CONFLICT);
        }else{
            userRegister.userRegisterToUser(passwordEncoder,accountRepo,userInfoRepo,userRepo);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<String> changeUserInfo(UserInfo infoUserNew,Errors errors){

        List listOfErrors = errorsToList(errors);

        if(listOfErrors.size() > 0) {
            return new ResponseEntity<>(listOfErrors.toString(), HttpStatus.NOT_ACCEPTABLE);
        }

        User loggedUser = getLoggedUser();
        UserInfo infoUser = loggedUser.getInfo();

        infoUser.setName(infoUserNew.getName());
        infoUser.setStreet(infoUserNew.getStreet());
        infoUser.setCity(infoUserNew.getCity()) ;
        infoUser.setZip(infoUserNew.getZip());
        infoUser.setPhone(infoUserNew.getPhone());

        userInfoRepo.save(infoUser);
        return new ResponseEntity<>("User information updated.",HttpStatus.ACCEPTED);

    }

    public ResponseEntity<String> loginToken(UserLogIn userLogIn) {

        Optional<User> user = userRepo.findByUserName(userLogIn.getUserName());

        if (user.isPresent()) {
            if (passwordEncoder.matches(userLogIn.getPassword(), user.get().getPassword())) {

                long time = System.currentTimeMillis();

                String token = Jwts.builder()
                        .claim("name", userLogIn.getUserName())
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

    public List errorsToList(Errors errors){
        List listOfErrors = new ArrayList<>();
        if(errors.hasErrors()){
            errors.getAllErrors().forEach(e -> listOfErrors.add(((FieldError) e).getField()));
        }
        return listOfErrors;
    }

    public User getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepo.findByUserName(currentPrincipalName);
        return user.get();
    }

}
