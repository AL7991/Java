package al.webapp.Other;

import al.webapp.Objects.Transaction;
import al.webapp.Objects.User;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Manager {
    private UserRepository userRepo;
    private UserInfoRepository userInfoRepo;
    private AccountRepository accountRepo;
    private checkBalance checkBal;

    @Autowired
    public Manager(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo, checkBalance checkBal) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
        this.checkBal = checkBal;
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

    public User saveUser(User user){
        accountRepo.save(user.getAccount());
        userInfoRepo.save(user.getInfo());

        return userRepo.save(user);
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






}
