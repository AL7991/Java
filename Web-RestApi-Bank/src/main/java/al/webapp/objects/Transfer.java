package al.webapp.objects;

import al.webapp.other.EnumValues;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Transfer extends Transaction{
    private Long accountReciverId;

    public Long getAccountReciverId() {
        return accountReciverId;
    }

    public void setAccountReciverId(Long accountReciverId) {
        this.accountReciverId = accountReciverId;
    }

    public Transfer(){
    }

    public Transfer(Long id, EnumValues.TransactionType transactionType, Long userAccountId, Long accountReciverId ,BigDecimal amount) {
        super(id, transactionType, userAccountId, amount);
        this.accountReciverId = accountReciverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(accountReciverId, transfer.accountReciverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountReciverId);
    }
}
