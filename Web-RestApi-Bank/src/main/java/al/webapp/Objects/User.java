package al.webapp.Objects;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "User_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;
    @OneToOne
    private Account account;
    @OneToOne
    private UserInfo info;

    private String role = "ROLE_USER";

    public User() {
    }

    public User(Long id, String userName, String password, Account account, UserInfo info) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.account = account;
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public String getRole() {return role; }

    public void setRole(String role) {this.role = "ROLE_" + role; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(account, user.account) && Objects.equals(info, user.info) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, account, info, role);
    }
}
