package app.controllers;

import app.models.Account;
import app.repositories.AccountRepository;
import app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;
    AccountRepository accountRepository;


    @GetMapping("/accounts")
    public ResponseEntity<List<Account>> getAllAccounts(@RequestParam(required = false) String nickname) {
        try {
            if(nickname==null){
                return new ResponseEntity<>(accountService.findAllAccounts(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(accountService.findAllAccountsThatContain(nickname), HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id) {
        Optional<Account> accountData = Optional.ofNullable(accountService.findAccountById(id));
        return accountData.map(account -> new ResponseEntity<>(account, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
            Account _account = accountService
                    .createAccount(new Account(account.getNickname()));
            return new ResponseEntity<>(_account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }


    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") long id, @RequestBody Account account) {
        return new ResponseEntity<>(accountService.updateAccount(id, account), HttpStatus.OK);

    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<List<Account>> deleteAllAccounts() {
        try {
            accountService.deleteAllAccounts();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/accounts/status")
    public ResponseEntity<List<Account>> findByStatus() {
        try {
            return new ResponseEntity<>(accountRepository.findByStatus(true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }



}

