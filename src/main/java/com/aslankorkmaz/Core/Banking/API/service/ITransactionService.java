package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.DepositRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.TransferRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.WithdrawRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransactionResponse;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransferResponse;
import org.springframework.stereotype.Service;

@Service
public interface ITransactionService {

    public TransactionResponse deposit(DepositRequest depositRequest);
    public TransactionResponse withdraw(WithdrawRequest withdrawRequest);
    public TransferResponse transfer(TransferRequest transferRequest);
}
