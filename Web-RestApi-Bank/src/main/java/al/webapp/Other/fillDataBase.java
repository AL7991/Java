package al.webapp.Other;

import al.webapp.Objects.Account;
import al.webapp.Objects.User;
import al.webapp.Objects.UserInfo;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class fillDataBase {
    private UserRepository userRepo;
    private UserInfoRepository userInfoRepo;
    private AccountRepository accountRepo;
    @Autowired
    public fillDataBase(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
    }

    @EventListener(classes = ApplicationReadyEvent.class)
    public void fillDb() {

        UserInfo userIn1 = new UserInfo(null, "Antek", "konwaliowa" , "Kraków", "05-525");
        UserInfo userIn2 = new UserInfo(null, "Bella", "kolska" , "Radom", "15-235");
        UserInfo userIn3 = new UserInfo(null, "Ula", "miedziana" , "Warszawa", "12-345");
        UserInfo userIn4 = new UserInfo(null, "Franek", "zlota" , "Wrocław", "02-853");

        userInfoRepo.save(userIn1);
        userInfoRepo.save(userIn2);
        userInfoRepo.save(userIn3);
        userInfoRepo.save(userIn4);

        Account account1 = new Account(null,new BigDecimal(5000));
        Account account2 = new Account(null,new BigDecimal(12000));
        Account account3 = new Account(null,new BigDecimal(2000));
        Account account4 = new Account(null,new BigDecimal(20000));

        accountRepo.save(account1);
        accountRepo.save(account2);
        accountRepo.save(account3);
        accountRepo.save(account4);

        User user1 = new User(null,"eraus", "asdfgh", account1, userIn1);
        User user2 = new User(null,"uran", "12345", account2, userIn2);
        User user3 = new User(null,"wenus", "1990", account3, userIn3);
        User user4 = new User(null,"kali", "franek", account4, userIn4);

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);

    }

}
