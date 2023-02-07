package al.webapp.Other;

import al.webapp.Objects.Transaction;
import al.webapp.Objects.User;
import al.webapp.Objects.UserLogIn;
import al.webapp.Objects.UserRegister;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Optional;

@Service
public class Manager {
    private UserRepository userRepo;
    private UserInfoRepository userInfoRepo;
    private AccountRepository accountRepo;
    private checkBalance checkBal;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Manager(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo, checkBalance checkBal, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
        this.checkBal = checkBal;
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
    public ResponseEntity<String> saveUser(UserRegister userRegister){

        Optional<User> user = userRepo.findByUserName(userRegister.getUserName());

        if (user.isPresent()) {

            return new ResponseEntity<>("User name is in used , change user name.", HttpStatus.NOT_ACCEPTABLE);
        }else{

            userRegister.userRegisterToUser(passwordEncoder,accountRepo,userInfoRepo,userRepo);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }
    }
    public void saveTransaction(Transaction transaction){
        try {
            boolean balance = checkBal.checkIfHaveCash(transaction.getAccountSender(), transaction.getAmount());
            if(balance){
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
    public ResponseEntity<String> login(UserLogIn userLogIn) {

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








}
