package al.webapp.managers;

import al.webapp.objects.Account;
import al.webapp.objects.Transfer;
import al.webapp.objects.User;
import al.webapp.other.WrongAccountNumberException;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.TransferRepository;
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
public class TransactionsManager {
    private AccountRepository accountRepo;
    private TransferRepository transactionRepo;

    private UserRepository userRepo;
    @Autowired
    public TransactionsManager(AccountRepository accountRepo, TransferRepository transactionRepo, UserRepository userRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }
    public boolean checkIfHaveCash(BigDecimal amount) throws WrongAccountNumberException {
        Account accountSender = loggedUserAccount();

            if(accountSender.getAmountOfMoney().compareTo(amount) > 0){
                return true;
            }else{
                return false;
            }
    }
    public void saveTransfer(Long recevier, BigDecimal amount) throws WrongAccountNumberException {
        Account accountSender = loggedUserAccount();
        Optional<Account> accountRecevier =accountRepo.findById(recevier);

        if(accountRecevier.isPresent()){
            accountSender.setAmountOfMoney(accountSender.getAmountOfMoney().subtract(amount));
            accountRecevier.get().setAmountOfMoney(accountRecevier.get().getAmountOfMoney().add(amount));
            accountRepo.save(accountSender);
            accountRepo.save(accountRecevier.get());

            transactionRepo.save(new Transfer(null, accountSender.getAccountNumber(), recevier, amount));

        }else {
            throw new WrongAccountNumberException();
        }

    }

    public ResponseEntity<String> doTransfer(Transfer transfer){
        try {
            boolean balance = checkIfHaveCash(transfer.getAmount());
            if(balance){
                try{
                    saveTransfer(transfer.getAccountReciver(), transfer.getAmount());
                    return new ResponseEntity<>("Transfer done.", HttpStatus.OK);
                } catch(WrongAccountNumberException e){
                    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>("The amount exceeds the balance of the account.", HttpStatus.BAD_REQUEST);
            }

        } catch (WrongAccountNumberException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    public Account loggedUserAccount(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> user = userRepo.findByUserName(currentPrincipalName);
        return user.get().getAccount();
    }

}
