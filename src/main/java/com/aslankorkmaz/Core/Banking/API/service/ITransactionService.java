package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.TransferRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.DepositRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.TransactionResponse;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.WithdrawRequest;
import org.springframework.stereotype.Service;

@Service
public interface ITransactionService {

    public TransactionResponse deposit(DepositRequest depositRequest);
    public TransactionResponse withdraw(WithdrawRequest withdrawRequest);
    public TransactionResponse transfer(TransferRequest transferRequest);
}
