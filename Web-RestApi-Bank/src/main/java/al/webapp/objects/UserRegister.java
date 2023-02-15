package al.webapp.objects;

import al.webapp.repository.AccountRepository;
import al.webapp.repository.UserInfoRepository;
import al.webapp.repository.UserRepository;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;
@Component
public class UserRegister {
    @NotBlank(message = "Providing your user name is mandatory.")
    @Size(max = 50 , message = "Name too long.")
    private String userName;

    @NotBlank(message = "Providing password is mandatory.")
    @Size(max = 50 , message = "Password too long.")
    private String password;

    @NotBlank(message = "Providing your name and surname is mandatory.")
    @Size(max = 50 , message = "Name too long.")
    private String name;

    @NotBlank(message="Providing street is mandatory.")
    @Size(max = 50 , message = "Name too long.")
    private String street;

    @NotBlank(message="Providing city is mandatory.")
    @Size(max = 50 , message = "Name too long.")
    private String city;

    @NotBlank(message="Providing zip code is mandatory.")
    @Pattern(regexp ="^[0-9][0-9][0-9][-]?[0-9][0-9][0-9]$" , message="Invalid zip code.")
    private String zip;
    @Digits(integer=9 , fraction=0 , message="Invalid phone number.")
    private String phone;
    @NotBlank(message="Providing amount of money is mandatory.")
    @Pattern(regexp ="^[1-9][0-9]*[,.]?[0-9]*$" , message="Invalid amount.")
    private String amountOfMoney;

    public UserRegister() { }
    public void userRegisterToUser(PasswordEncoder passEncoder, AccountRepository accountRepo, UserInfoRepository userInfoRepo, UserRepository userRepo){
        String amountOfMoneyReplace = amountOfMoney.replace(",", ".");
        Account account = new Account(null, new BigDecimal(amountOfMoneyReplace));
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

    public String getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(String amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegister that = (UserRegister) o;
        return Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(zip, that.zip) && Objects.equals(phone, that.phone) && Objects.equals(amountOfMoney, that.amountOfMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, name, street, city, zip, phone, amountOfMoney);
    }
}
