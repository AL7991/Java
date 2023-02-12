package al.webapp.other;

import al.webapp.objects.Account;
import al.webapp.objects.User;
import al.webapp.objects.UserInfo;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FillDataBase {
    private UserRepository userRepo;
    private UserInfoRepository userInfoRepo;
    private AccountRepository accountRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public FillDataBase(UserRepository userRepo, UserInfoRepository userInfoRepo, AccountRepository accountRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userInfoRepo = userInfoRepo;
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(classes = ApplicationReadyEvent.class)
    public void fillDb() {

        UserInfo userIn1 = new UserInfo(null, "Antek", "konwaliowa" , "Kraków", "05-525","316258396");
        UserInfo userIn2 = new UserInfo(null, "Bella", "kolska" , "Radom", "15-235","946285182");
        UserInfo userIn3 = new UserInfo(null, "Ula", "miedziana" , "Warszawa", "12-345","205418264");
        UserInfo userIn4 = new UserInfo(null, "Franek", "zlota" , "Wrocław", "02-853","946283164");

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

        User user1 = new User(null,"eraus", passwordEncoder.encode("asdfgh"), account1, userIn1);
        User user2 = new User(null,"uran", passwordEncoder.encode("12345"), account2, userIn2);
        User user3 = new User(null,"wenus", passwordEncoder.encode("1990"), account3, userIn3);
        User user4 = new User(null,"kali", passwordEncoder.encode("franek"), account4, userIn4);

        user1.setRole("ADMIN");

        userRepo.save(user1);
        userRepo.save(user2);
        userRepo.save(user3);
        userRepo.save(user4);

    }

}
