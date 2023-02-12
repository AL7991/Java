package al.webapp.objects;

import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

public class UserRegister {

    private String userName;

    private String password;

    private String name;

    private String street;

    private String city;

    private String zip;

    private String phone;

    private BigDecimal amountOfMoney;

    public UserRegister() { }
    public void userRegisterToUser(PasswordEncoder passEncoder, AccountRepository accountRepo, UserInfoRepository userInfoRepo, UserRepository userRepo){
        Account account = new Account(null, amountOfMoney);
        accountRepo.save(account);

        UserInfo userInfo = new UserInfo(null, name, street, city, zip, phone);
        userInfoRepo.save(userInfo);

        User user = new User(null, userName, passEncoder.encode(password), account, userInfo);
        userRepo.save(user);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(BigDecimal amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }




}
