package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.transaction.DepositRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.TransactionCreateDto;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.TransactionResponse;
import org.springframework.stereotype.Service;

@Service
public interface ITransactionService {

    public TransactionResponse deposit(DepositRequest depositRequest);
    public TransactionResponse withdraw(TransactionCreateDto transactionCreateDto);
    public TransactionResponse transfer(TransactionCreateDto transactionCreateDto);
}
