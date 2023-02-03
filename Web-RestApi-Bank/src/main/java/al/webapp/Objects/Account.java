package al.webapp.Objects;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "User_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;
    private BigDecimal amountOfMoney;

    public Account() {
    }

    public Account(Long accountNumber, BigDecimal amountOfMoney) {
        this.accountNumber = accountNumber;
        this.amountOfMoney = amountOfMoney;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(BigDecimal amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) && Objects.equals(amountOfMoney, account.amountOfMoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amountOfMoney);
    }
}
