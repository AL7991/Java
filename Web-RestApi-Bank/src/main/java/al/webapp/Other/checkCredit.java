package al.webapp.Other;

import al.webapp.Objects.Account;
import al.webapp.Objects.User;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class checkCredit {

    private UserRepository userRepo;
    private AccountRepository accountRepo;
    private final BigDecimal maxAmountOfCredit = new BigDecimal(5000);

    @Autowired
    public checkCredit(UserRepository userRepo, AccountRepository accountRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
    }

    public ResponseEntity<String> takeCredit(BigDecimal amount){

        Account account = loggedUserAccount();

        if(account.ifAlreadyHaveCredit()){
            return new ResponseEntity<>("You have to pay off the loan to take another one.", HttpStatus.NOT_ACCEPTABLE);
        }else{
            if(amount.compareTo(maxAmountOfCredit) <= 0){
                account.setAmountOfMoney(account.getAmountOfMoney().add(amount));
                account.setAmountOfCredit(amount);
                account.setAlreadyHaveCredit(true);
                accountRepo.save(account);

                return new ResponseEntity<>("Credit granted.", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("The sum is too large. The maximum loan amount is 5000.", HttpStatus.BAD_REQUEST);
            }

        }

    }

    public ResponseEntity<String> repaymentPartOfCredit(BigDecimal amount){

        Account account = loggedUserAccount();

        if(account.ifAlreadyHaveCredit()){

            if(account.getAmountOfMoney().compareTo(amount) >= 0){

                if(account.getAmountOfCredit().compareTo(amount) > 0){

                    account.setAmountOfMoney(account.getAmountOfMoney().subtract(amount));
                    account.setAmountOfCredit(account.getAmountOfCredit().subtract(amount));
                    accountRepo.save(account);

                    return new ResponseEntity<>("Payment accepted", HttpStatus.OK);

                }else{

                    payAllCredit(account);

                    return new ResponseEntity<>("The loan has been repaid.", HttpStatus.OK);
                }

            }else{
                return new ResponseEntity<>("You don't have that amount", HttpStatus.NOT_ACCEPTABLE);
            }

        }else{
            return new ResponseEntity<>("You have no credit", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> repaymentAllOfCredit() {

        Account account = loggedUserAccount();

        if(account.ifAlreadyHaveCredit()){

            if(account.getAmountOfMoney().compareTo(account.getAmountOfCredit()) >= 0){

                payAllCredit(account);

                return new ResponseEntity<>("The loan has been repaid.", HttpStatus.OK);

            }else{
                return new ResponseEntity<>("You don't have that amount", HttpStatus.NOT_ACCEPTABLE);
            }


        }else{
            return new ResponseEntity<>("You have no credit", HttpStatus.BAD_REQUEST);
        }



    }



    private Account loggedUserAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepo.findByUserName(currentPrincipalName);
        return user.get().getAccount();
    }


    private void payAllCredit(Account account){

        account.setAmountOfMoney(account.getAmountOfMoney().subtract(account.getAmountOfCredit()));
        account.setAmountOfCredit(new BigDecimal(0));
        account.setAlreadyHaveCredit(false);
        accountRepo.save(account);

    }






}
