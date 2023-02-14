package al.webapp.objects;

import al.webapp.other.EnumValues;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EnumValues.TransactionType transactionType;

    private final LocalDate dateOfTransaction = LocalDate.now();

    private final LocalTime timeOfTransaction = LocalTime.now();

    private Long userAccountId;

    private BigDecimal amount;

    public Transaction(){
    }

    public Transaction(Long id, EnumValues.TransactionType transactionType, Long userAccountId, BigDecimal amount) {
        this.id = id;
        this.transactionType = transactionType;
        this.userAccountId = userAccountId;
        this.amount = amount;
    }

    public EnumValues.TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(EnumValues.TransactionType transactionType) {this.transactionType = transactionType; }

    public LocalDate getDateOfTransaction() {return dateOfTransaction; }

    public LocalTime getTimeOfTransaction() {return timeOfTransaction; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
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
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && transactionType == that.transactionType && Objects.equals(dateOfTransaction, that.dateOfTransaction) && Objects.equals(timeOfTransaction, that.timeOfTransaction) && Objects.equals(userAccountId, that.userAccountId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, dateOfTransaction, timeOfTransaction, userAccountId, amount);
    }
}
