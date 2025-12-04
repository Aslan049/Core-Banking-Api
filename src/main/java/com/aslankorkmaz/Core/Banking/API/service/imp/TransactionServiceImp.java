package com.aslankorkmaz.Core.Banking.API.service.imp;

import com.aslankorkmaz.Core.Banking.API.dto.transaction.DepositRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.TransactionCreateDto;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.TransactionResponse;
import com.aslankorkmaz.Core.Banking.API.entity.account.Account;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.Transaction;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionStatusEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionType;
import com.aslankorkmaz.Core.Banking.API.exception.AccountNotFoundException;
import com.aslankorkmaz.Core.Banking.API.exception.BadRequestException;
import com.aslankorkmaz.Core.Banking.API.repository.IAccountRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ICustomerRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ITransactionRepository;
import com.aslankorkmaz.Core.Banking.API.service.ITransactionService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;


@Service
public class TransactionServiceImp implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final ICustomerRepository customerRepository;
    private final IAccountRepository accountRepository;
    @Autowired
    public TransactionServiceImp(ITransactionRepository transactionRepository, ICustomerRepository customerRepository, IAccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    @Transactional
    public TransactionResponse deposit(DepositRequest depositRequest) {
        if(depositRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be > 0");
        }

        Account account = accountRepository.findByIban(depositRequest.getToIban())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
       if(!account.getCurrency().equals(depositRequest.getCurrency())) {
           throw new BadRequestException("Currencies do not match");
       }
       account.setBalance(account.getBalance().add(depositRequest.getAmount()));
       Account savedAccount = accountRepository.save(account);

       Transaction transaction = new Transaction();
       transaction.setId(savedAccount.getId());
       transaction.setFromIban(null);
       transaction.setToIban(savedAccount.getIban());
       transaction.setAmount(depositRequest.getAmount());
       transaction.setCurrency(savedAccount.getCurrency());
       transaction.setType(TransactionType.DEPOSIT);
       transaction.setStatusEnum(TransactionStatusEnum.SUCCESS);
       transaction.setDescription(depositRequest.getDescription());
       transaction.setCreatedAt(Instant.now());

       Transaction savedTransaction = transactionRepository.save(transaction);

       TransactionResponse transactionResponse = new TransactionResponse();
       transactionResponse.setDescription(savedTransaction.getDescription());
       transactionResponse.setFromIban(null);
       transactionResponse.setId(savedTransaction.getId());
       transactionResponse.setAmount(savedTransaction.getAmount());
       transactionResponse.setToIban(savedTransaction.getToIban());
       transactionResponse.setCurrency(savedTransaction.getCurrency());
       transactionResponse.setType(TransactionType.DEPOSIT);
       transactionResponse.setStatusEnum(TransactionStatusEnum.SUCCESS);
       transactionResponse.setCreatedAt(savedTransaction.getCreatedAt());


        return transactionResponse;
    }

    @Override
    @Transactional
    public TransactionResponse withdraw(TransactionCreateDto transactionCreateDto) {
        return null;
    }

    @Override
    @Transactional
    public TransactionResponse transfer(TransactionCreateDto transactionCreateDto) {

        return null;
    }
}
