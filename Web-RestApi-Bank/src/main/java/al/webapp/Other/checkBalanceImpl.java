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
    public boolean checkIfHaveCash(Long sender , Long recevier , BigDecimal amount) {
        Optional<Account> accountSender = accountRepo.findById(sender);
        Optional<Account> accountRcevier =accountRepo.findById(recevier);

        if(accountSender.isPresent() && accountRcevier.isPresent()){
            if(accountSender.get().getAmountOfMoney().compareTo(amount) > 0){
                accountSender.get().setAmountOfMoney(accountSender.get().getAmountOfMoney().subtract(amount));
                accountRcevier.get().setAmountOfMoney(accountRcevier.get().getAmountOfMoney().add(amount));
            }

        }

        return false;
    }
}
