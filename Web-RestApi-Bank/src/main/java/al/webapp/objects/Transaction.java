package al.webapp.objects;

import al.webapp.other.EnumValues;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EnumValues.TransactionType transactionType;
    private Long accountSender;

    private Long accountReciver;

    private BigDecimal amount;

    public Transaction() {
    }

    public Transaction(Long id, EnumValues.TransactionType transactionType, Long accountSender, Long accountReciver, BigDecimal amount) {
        this.id = id;
        this.transactionType = transactionType;
        this.accountSender = accountSender;
        this.accountReciver = accountReciver;
        this.amount = amount;
    }

    public EnumValues.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(EnumValues.TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountSender() {
        return accountSender;
    }

    public void setAccountSender(Long accountSender) {
        this.accountSender = accountSender;
    }

    public Long getAccountReciver() {
        return accountReciver;
    }

    public void setAccountReciver(Long accountReciver) {
        this.accountReciver = accountReciver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return Objects.equals(id, transaction.id) && transactionType == transaction.transactionType && Objects.equals(accountSender, transaction.accountSender) && Objects.equals(accountReciver, transaction.accountReciver) && Objects.equals(amount, transaction.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, accountSender, accountReciver, amount);
    }
}
