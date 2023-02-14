package al.webapp.managers;

import al.webapp.objects.Account;
import al.webapp.objects.Transaction;
import al.webapp.other.EnumValues;
import al.webapp.repository.AccountRepository;
import al.webapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CreditManager {
    private TransactionsManager transactionsManager;
    private AccountRepository accountRepo;
    private TransactionRepository transactionRepo;
    private final BigDecimal maxAmountOfCredit = new BigDecimal(5000);

    @Autowired
    public CreditManager(TransactionsManager transactionsManager, AccountRepository accountRepo, TransactionRepository transactionRepo) {
        this.transactionsManager = transactionsManager;
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    public ResponseEntity<String> takeCredit(BigDecimal amount){

        Account account = transactionsManager.loggedUserAccount();

        if(account.ifAlreadyHaveCredit()){
            return new ResponseEntity<>("You have to pay off the loan to take another one.", HttpStatus.NOT_ACCEPTABLE);
        }else{
            if(amount.compareTo(maxAmountOfCredit) <= 0){
                account.setAmountOfMoney(account.getAmountOfMoney().add(amount));
                account.setAmountOfCredit(amount);
                account.setAlreadyHaveCredit(true);

                Transaction transaction = new Transaction(null, EnumValues.TransactionType.TAKECREDIT, account.getAccountNumber(), amount);
                transactionRepo.save(transaction);
                account.addTransactionToTransactionHistory(transaction);

                accountRepo.save(account);

                return new ResponseEntity<>("Credit granted.", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("The sum is too large. The maximum loan amount is 5000.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    public ResponseEntity<String> repaymentPartOfCredit(BigDecimal amount){return checkPayment(EnumValues.PaymentType.PARTPAYMENT,amount); }

    public ResponseEntity<String> repaymentAllOfCredit() {return checkPayment(EnumValues.PaymentType.ALLPAYMENT,null); }

    private ResponseEntity<String> checkPayment(EnumValues.PaymentType paymentType,BigDecimal amount){

        Account account = transactionsManager.loggedUserAccount();

        if(account.ifAlreadyHaveCredit()){
            switch (paymentType) {
                case ALLPAYMENT:
                    if(account.getAmountOfMoney().compareTo(account.getAmountOfCredit()) >= 0){
                        payAllCredit(account);

                        return new ResponseEntity<>("The loan has been repaid.", HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>("The amount exceeds the balance of the account.", HttpStatus.NOT_ACCEPTABLE);
                    }
                case PARTPAYMENT:
                    if(account.getAmountOfMoney().compareTo(amount) >= 0){
                        if(account.getAmountOfCredit().compareTo(amount) > 0){
                            account.setAmountOfMoney(account.getAmountOfMoney().subtract(amount));
                            account.setAmountOfCredit(account.getAmountOfCredit().subtract(amount));

                            Transaction transaction = new Transaction(null, EnumValues.TransactionType.REPAYMENTCREDIT, account.getAccountNumber(), amount);
                            transactionRepo.save(transaction);
                            account.addTransactionToTransactionHistory(transaction);

                            accountRepo.save(account);

                            return new ResponseEntity<>("Payment accepted", HttpStatus.OK);
                        }else{
                            payAllCredit(account);

                            return new ResponseEntity<>("The loan has been repaid.", HttpStatus.OK);
                        }
                    }else{
                        return new ResponseEntity<>("The amount exceeds the balance of the account.", HttpStatus.NOT_ACCEPTABLE);
                    }
                default:
                    return new ResponseEntity<>("Something went wrong.", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("You have no credit", HttpStatus.BAD_REQUEST);
        }
    }

    private void payAllCredit(Account account){
        account.setAmountOfMoney(account.getAmountOfMoney().subtract(account.getAmountOfCredit()));
        Transaction transaction = new Transaction(null, EnumValues.TransactionType.REPAYMENTCREDIT, account.getAccountNumber(),account.getAmountOfCredit());
        transactionRepo.save(transaction);
        account.addTransactionToTransactionHistory(transaction);
        account.setAmountOfCredit(new BigDecimal(0));
        account.setAlreadyHaveCredit(false);

        accountRepo.save(account);
    }
}

