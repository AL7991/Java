package al.webapp.managers;

import al.webapp.objects.Account;
import al.webapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CreditManager {
    private TransactionsManager transactionsManager;
    private AccountRepository accountRepo;
    private final BigDecimal maxAmountOfCredit = new BigDecimal(5000);

    @Autowired
    public CreditManager(TransactionsManager transactionsManager, AccountRepository accountRepo) {
        this.transactionsManager = transactionsManager;
        this.accountRepo = accountRepo;
    }

    public ResponseEntity<String> takeCredit(BigDecimal amount){

        Account account = transactionsManager.loggedUserAccount();

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
        return checkPayment(Options.partPayment,amount);
    }

    public ResponseEntity<String> repaymentAllOfCredit() {
        return checkPayment(Options.allPayment,null);
    }

    private ResponseEntity<String> checkPayment(Options option,BigDecimal amount){

        Account account = transactionsManager.loggedUserAccount();

        if(account.ifAlreadyHaveCredit()){
            switch (option) {
                case allPayment:
                    if(account.getAmountOfMoney().compareTo(account.getAmountOfCredit()) >= 0){
                        payAllCredit(account);

                        return new ResponseEntity<>("The loan has been repaid.", HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>("The amount exceeds the balance of the account.", HttpStatus.NOT_ACCEPTABLE);
                    }
                case partPayment:
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
                        return new ResponseEntity<>("The amount exceeds the balance of the account.", HttpStatus.NOT_ACCEPTABLE);
                    }
                default:
                    return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("You have no credit", HttpStatus.BAD_REQUEST);
        }
    }

    private void payAllCredit(Account account){
        account.setAmountOfMoney(account.getAmountOfMoney().subtract(account.getAmountOfCredit()));
        account.setAmountOfCredit(new BigDecimal(0));
        account.setAlreadyHaveCredit(false);
        accountRepo.save(account);
    }
}
 enum Options{
    partPayment,allPayment
}
