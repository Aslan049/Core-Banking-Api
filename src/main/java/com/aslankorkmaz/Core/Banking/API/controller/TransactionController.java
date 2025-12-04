package com.aslankorkmaz.Core.Banking.API.controller;

import com.aslankorkmaz.Core.Banking.API.dto.transaction.DepositRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.TransactionCreateDto;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.TransactionResponse;
import com.aslankorkmaz.Core.Banking.API.service.ITransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {

    @Autowired
    private final ITransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        return ResponseEntity.ok(transactionService.deposit(depositRequest));
    }

}
