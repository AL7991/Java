package al.webapp.objects;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "User_account")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;
    private BigDecimal amountOfMoney;
    private boolean alreadyHaveCredit = false;
    private BigDecimal amountOfCredit = new BigDecimal(0);

    @ManyToMany(targetEntity = Transaction.class)
    private List<Transaction> transactionHistory = new ArrayList<Transaction>();

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

    public boolean ifAlreadyHaveCredit() {
        return alreadyHaveCredit;
    }

    public void setAlreadyHaveCredit(boolean alreadyHaveCredit) {
        this.alreadyHaveCredit = alreadyHaveCredit;
    }

    public BigDecimal getAmountOfCredit() {
        return amountOfCredit;
    }

    public void setAmountOfCredit(BigDecimal amountOfCredit) {
        this.amountOfCredit = amountOfCredit;
    }

    public List getTransactionHistory() {return transactionHistory; }

    public void addTransactionToTransactionHistory(Transaction transaction) {this.transactionHistory.add(transaction) ; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return alreadyHaveCredit == account.alreadyHaveCredit && Objects.equals(accountNumber, account.accountNumber) && Objects.equals(amountOfMoney, account.amountOfMoney) && Objects.equals(amountOfCredit, account.amountOfCredit) && Objects.equals(transactionHistory, account.transactionHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amountOfMoney, alreadyHaveCredit, amountOfCredit, transactionHistory);
    }
}
