package app.controllers;

import app.models.Account;
import app.models.AccountTransaction;
import app.service.AccountService;
import app.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AccountTransactionController {

    @Autowired
    private AccountTransactionService accountTransactionService;

    @Autowired
    private AccountService accountService;

    public AccountTransactionController(AccountTransactionService accountTransactionService, AccountService accountService){
        this.accountTransactionService = accountTransactionService;
        this.accountService = accountService;
    }

    @GetMapping("accounts/{accountId}/transactions")
    public ResponseEntity<List<AccountTransaction>> getAllTransactions(@PathVariable Long accountId){
        return new ResponseEntity<>(accountTransactionService.findAllTransactions(accountId), HttpStatus.OK);
    }

    @PutMapping("accounts/{accountId}/deposit")
    public ResponseEntity<AccountTransaction> putDepositToAccount(@PathVariable Long accountId, @RequestParam Double amount, @RequestBody Account account){

        return new ResponseEntity<>(accountTransactionService.depositToAccount(accountId,account,amount), HttpStatus.OK);
    }

    @PutMapping("accounts/{accountId}/withdraw")
    public ResponseEntity<AccountTransaction> withdraw(@PathVariable Long accountId,@RequestParam Double amount, @RequestBody Account account){
        return new ResponseEntity<>(accountTransactionService.withdrawFromAccount(accountId,account,amount),HttpStatus.OK);
    }

    @PutMapping("accounts/transfer")
    public ResponseEntity<List<Account>> transfer(){
        return new ResponseEntity<>(accountService.findAllAccounts(),HttpStatus.OK);
    }

}
