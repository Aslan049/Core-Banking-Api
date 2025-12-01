package com.aslankorkmaz.Core.Banking.API.service.imp;

import com.aslankorkmaz.Core.Banking.API.dto.account.AccountCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.account.AccountResponse;
import com.aslankorkmaz.Core.Banking.API.entity.account.Account;
import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
import com.aslankorkmaz.Core.Banking.API.entity.account.MoneyTypeEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.Transaction;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionStatusEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionType;
import com.aslankorkmaz.Core.Banking.API.exception.CustomerNotFoundException;
import com.aslankorkmaz.Core.Banking.API.repository.IAccountRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ICustomerRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ITransactionRepository;
import com.aslankorkmaz.Core.Banking.API.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;
    private final ITransactionRepository transactionRepository;
    @Autowired
    public AccountService(IAccountRepository accountRepository,ICustomerRepository customerRepository,ITransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Account account = new Account();
        account.setIban(generateIban());
        account.setCustomer(customer);
        account.setCurrency(MoneyTypeEnum.TRY);
        account.setBalance(request.getInitialDeposit() == null ? BigDecimal.ZERO : request.getInitialDeposit());
        Account saveAccount = accountRepository.save(account);


        if(request.getInitialDeposit() != null && request.getInitialDeposit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction();
            transaction.setFromIban(null);
            transaction.setToIban(saveAccount.getIban());
            transaction.setAmount(request.getInitialDeposit());
            transaction.setCurrency(request.getCurrency());
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setStatusEnum(TransactionStatusEnum.SUCCESS);
            transactionRepository.save(transaction);
        }

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setIban(saveAccount.getIban());
        accountResponse.setCurrency(saveAccount.getCurrency().toString());
        accountResponse.setBalance(saveAccount.getBalance());
        accountResponse.setCustomerId(saveAccount.getCustomer().getId());
        accountResponse.setId(saveAccount.getId());
        accountResponse.setInitialDeposit(request.getInitialDeposit());

        return accountResponse;
    }

    private String generateIban() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
