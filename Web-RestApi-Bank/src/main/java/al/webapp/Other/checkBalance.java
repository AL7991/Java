package al.webapp.Other;

import al.webapp.Objects.Account;
import al.webapp.Objects.Transaction;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
@Service
public class checkBalance{
    private AccountRepository accountRepo;

    private TransactionRepository transactionRepo;
    @Autowired
    public checkBalance(AccountRepository accountRepo, TransactionRepository transactionRepo) {
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }
    public boolean checkIfHaveCash(Long sender , BigDecimal amount) throws wrongAccountNumberException {
        Optional<Account> accountSender = accountRepo.findById(sender);

        if(accountSender.isPresent()){
            if(accountSender.get().getAmountOfMoney().compareTo(amount) > 0){
                return true;
            }else{
                return false;
            }
        } else {
            throw new wrongAccountNumberException();
        }
    }

    public void doTransaction(Long sender, Long recevier, BigDecimal amount) throws wrongAccountNumberException {
        Optional<Account> accountSender = accountRepo.findById(sender);
        Optional<Account> accountRcevier =accountRepo.findById(recevier);

        if(accountRcevier.isPresent()){
            accountSender.get().setAmountOfMoney(accountSender.get().getAmountOfMoney().subtract(amount));
            accountRcevier.get().setAmountOfMoney(accountRcevier.get().getAmountOfMoney().add(amount));
            accountRepo.save(accountSender.get());
            accountRepo.save(accountRcevier.get());

            transactionRepo.save(new Transaction(null, sender, recevier, amount));

        }else {
            throw new wrongAccountNumberException();
        }

    }
}
