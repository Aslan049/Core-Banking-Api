package com.aslankorkmaz.Core.Banking.API.controller;
import com.aslankorkmaz.Core.Banking.API.dto.account.AccountCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.account.AccountResponse;
import com.aslankorkmaz.Core.Banking.API.service.IAccountService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final IAccountService accountService;
    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountCreateRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{iban}")
    public ResponseEntity<AccountResponse> getAccountByIban(@PathVariable String iban) {
        return ResponseEntity.ok(accountService.getAccountByIban(iban));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts() {
        return ResponseEntity.ok(accountService.getAccounts());
    }
}
