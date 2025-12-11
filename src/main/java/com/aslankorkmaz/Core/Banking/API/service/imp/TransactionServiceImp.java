package com.aslankorkmaz.Core.Banking.API.service.imp;

import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.DepositRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransactionResponse;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.TransferRequest;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.response.TransferResponse;
import com.aslankorkmaz.Core.Banking.API.dto.transaction.request.WithdrawRequest;
import com.aslankorkmaz.Core.Banking.API.entity.account.Account;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.Transaction;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionStatusEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionType;
import com.aslankorkmaz.Core.Banking.API.exception.AccountNotFoundException;
import com.aslankorkmaz.Core.Banking.API.exception.BadRequestException;
import com.aslankorkmaz.Core.Banking.API.exception.InsufficientFundsException;
import com.aslankorkmaz.Core.Banking.API.repository.IAccountRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ICustomerRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ITransactionRepository;
import com.aslankorkmaz.Core.Banking.API.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;


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

       //Add money to account
       account.setBalance(account.getBalance().add(depositRequest.getAmount()));
       Account savedAccount = accountRepository.save(account);

       Transaction transaction = new Transaction();
       transaction.setAccountId(savedAccount.getId());
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
    public TransactionResponse withdraw(WithdrawRequest withdrawRequest) {
        if(withdrawRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be > 0");
        }

       Account account = accountRepository.findByIban(withdrawRequest.getFromIban())
               .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(!account.getCurrency().equals(withdrawRequest.getCurrency())) {
            throw new BadRequestException("Currencies do not match");
        }

        if(account.getBalance().compareTo(withdrawRequest.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        // subtract money from the account.
        account.setBalance(account.getBalance().subtract(withdrawRequest.getAmount()));

        Account savedAccount = accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setId(savedAccount.getId());
        transaction.setToIban(savedAccount.getIban());
        transaction.setAmount(withdrawRequest.getAmount());
        transaction.setCurrency(savedAccount.getCurrency());
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setStatusEnum(TransactionStatusEnum.SUCCESS);
        transaction.setDescription(withdrawRequest.getDescription());
        transaction.setCreatedAt(Instant.now());
        transaction.setAccountId(savedAccount.getId());


        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setDescription(savedTransaction.getDescription());
        transactionResponse.setToIban(savedTransaction.getToIban());
        transactionResponse.setId(savedTransaction.getId());
        transactionResponse.setAmount(savedTransaction.getAmount());
        transactionResponse.setCurrency(savedTransaction.getCurrency());
        transactionResponse.setType(TransactionType.WITHDRAW);
        transactionResponse.setStatusEnum(TransactionStatusEnum.SUCCESS);
        transactionResponse.setCreatedAt(savedTransaction.getCreatedAt());
        transactionResponse.setUpdatedBalance(savedAccount.getBalance());


        return transactionResponse;
    }

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest) {
        if (transferRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Amount must be > 0");
        }

        if(Objects.equals(transferRequest.getFromIban(), transferRequest.getToIban())) {
            throw new BadRequestException("From and To Iban account must not be the same");
        }

        Account fromIbanAccount = accountRepository.findByIban(transferRequest.getFromIban())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        Account toIbanAccount = accountRepository.findByIban(transferRequest.getToIban())
                .orElseThrow(() -> new AccountNotFoundException("Account which reaches not found"));

        if(!fromIbanAccount.getCurrency().equals(transferRequest.getCurrency()) || !toIbanAccount.getCurrency().equals(transferRequest.getCurrency())) {
            throw new BadRequestException("Currencies do not match");
        }

        if(fromIbanAccount.getBalance().compareTo(transferRequest.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        fromIbanAccount.setBalance(fromIbanAccount.getBalance().subtract(transferRequest.getAmount()));
        toIbanAccount.setBalance(toIbanAccount.getBalance().add(transferRequest.getAmount()));

        Account savedFromIban = accountRepository.save(fromIbanAccount);
        Account savedToIban = accountRepository.save(toIbanAccount);

        Transaction transaction = new Transaction();
        transaction.setFromIban(savedFromIban.getIban());
        transaction.setToIban(savedToIban.getIban());
        transaction.setAmount(transferRequest.getAmount());
        transaction.setCurrency(transferRequest.getCurrency());
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatusEnum(TransactionStatusEnum.SUCCESS);
        transaction.setDescription(transferRequest.getDescription());
        transaction.setCreatedAt(Instant.now());
        transaction.setAccountId(savedFromIban.getId());

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setFromIbanId(fromIbanAccount.getId());
        transferResponse.setToIbanId(toIbanAccount.getId());
        transferResponse.setDescription(savedTransaction.getDescription());
        transferResponse.setFromIban(savedFromIban.getIban());
        transferResponse.setToIban(savedToIban.getIban());
        transferResponse.setId(savedTransaction.getId());
        transferResponse.setAmount(savedTransaction.getAmount());
        transferResponse.setCurrency(savedTransaction.getCurrency());
        transferResponse.setType(TransactionType.TRANSFER);
        transferResponse.setStatusEnum(TransactionStatusEnum.SUCCESS);
        transferResponse.setCreatedAt(savedTransaction.getCreatedAt());

        return transferResponse;
    }




















}
