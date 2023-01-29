package al.webapp.Other;

import al.webapp.Objects.Account;
import al.webapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

public class checkBalanceImpl implements checkBalance{

    private AccountRepository accountRepo;

    @Autowired
    public checkBalanceImpl(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
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

    @Override
    public void doTransaction(Long sender, Long recevier, BigDecimal amount) throws wrongAccountNumberException {
        Optional<Account> accountSender = accountRepo.findById(sender);
        Optional<Account> accountRcevier =accountRepo.findById(recevier);

        if(accountRcevier.isPresent()){
            accountSender.get().setAmountOfMoney(accountSender.get().getAmountOfMoney().subtract(amount));
            accountRcevier.get().setAmountOfMoney(accountRcevier.get().getAmountOfMoney().add(amount));
        }else {
            throw new wrongAccountNumberException();
        }

    }
}
