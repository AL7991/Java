package al.webapp.controllers;

import al.webapp.managers.CreditManager;
import al.webapp.managers.TransactionsManager;
import al.webapp.objects.Transaction;
import al.webapp.objects.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private CreditManager creditManager;
    private TransactionsManager transactionsManager;

    @Autowired
    public TransactionController(CreditManager creditManager, TransactionsManager transactionsManager) {
        this.creditManager = creditManager;
        this.transactionsManager = transactionsManager;
    }

    @RequestMapping("/transfer")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> doTransfer(@RequestBody Transfer transfer){return transactionsManager.doTransfer(transfer); }

    @RequestMapping("/takeCredit")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> takeCredit(@RequestBody BigDecimal amount) {return creditManager.takeCredit(amount); }

    @RequestMapping("/repaymentPartOfCredit")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> repaymentPartOfCredit(@RequestBody BigDecimal amount) {return creditManager.repaymentPartOfCredit(amount); }

    @GetMapping("/repaymentAllOfCredit")
    public ResponseEntity<String> repaymentAllOfCredit() {return creditManager.repaymentAllOfCredit(); }

    @GetMapping("/history/all")
    public List<Transaction> transactionHistoryAll(){return transactionsManager.getAllTransactionHistory(); }

    @GetMapping("/history/page/{page}")
    public List<Transaction> transactionHistoryOfPage(@PathVariable("page") int page){return transactionsManager.getTransactionHistoryOfPage(page); }

}
