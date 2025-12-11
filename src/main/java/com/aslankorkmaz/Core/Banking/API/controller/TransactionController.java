package com.aslankorkmaz.Core.Banking.API.controller;

import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.DepositRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.TransferRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.WithdrawRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransactionResponse;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransferResponse;
import com.aslankorkmaz.Core.Banking.API.service.ITransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.deposit(depositRequest));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.withdraw(withdrawRequest));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.transfer(transferRequest));
    }

}
