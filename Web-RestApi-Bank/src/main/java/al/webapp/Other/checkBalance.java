package al.webapp.Other;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface checkBalance {

    public boolean checkIfHaveCash(Long sender , BigDecimal amount) throws wrongAccountNumberException;

    public void doTransaction(Long sender , Long recevier , BigDecimal amount) throws wrongAccountNumberException;

}
