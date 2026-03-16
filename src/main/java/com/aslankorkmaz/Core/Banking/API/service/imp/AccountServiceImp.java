package com.aslankorkmaz.Core.Banking.API.service.imp;

import com.aslankorkmaz.Core.Banking.API.dto.account.AccountCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.account.AccountResponse;
import com.aslankorkmaz.Core.Banking.API.entity.account.Account;
import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
import com.aslankorkmaz.Core.Banking.API.entity.account.Currency;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.Transaction;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionStatusEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionType;
import com.aslankorkmaz.Core.Banking.API.exception.AccountAlreadyExistsException;
import com.aslankorkmaz.Core.Banking.API.exception.AccountNotFoundException;
import com.aslankorkmaz.Core.Banking.API.exception.CustomerNotFoundException;
import com.aslankorkmaz.Core.Banking.API.mapper.AccountMapper;
import com.aslankorkmaz.Core.Banking.API.repository.IAccountRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ICustomerRepository;
import com.aslankorkmaz.Core.Banking.API.repository.ITransactionRepository;
import com.aslankorkmaz.Core.Banking.API.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImp implements IAccountService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;
    private final ITransactionRepository transactionRepository;
    private final AccountMapper accountMapper;
    @Autowired
    public AccountServiceImp(IAccountRepository accountRepository, ICustomerRepository customerRepository, ITransactionRepository transactionRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public AccountResponse createAccount(AccountCreateRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Currency moneyTypeEnum = Currency.fromString(request.getCurrency().toString());

        if(accountRepository.existsByCustomerAndCurrency(customer,moneyTypeEnum)) {
            throw new AccountAlreadyExistsException(customer.getFirstName() + " " + customer.getLastName() + " has account Already Exists " + moneyTypeEnum.name());
        }

        /*
        Account account = new Account();
        account.setIban(generateIban());
        account.setCustomer(customer);
        account.setCurrency(moneyTypeEnum);
        account.setBalance(request.getInitialDeposit() == null ? BigDecimal.ZERO : request.getInitialDeposit());
        Account saveAccount = accountRepository.save(account);

         */
        Account account = accountMapper.toEntity(request);
        account.setCustomer(customer);
        account.setIban(generateIban());
        Account savedAccount = accountRepository.save(account);


        if(request.getInitialDeposit() != null && request.getInitialDeposit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = new Transaction();
            transaction.setFromIban(null);
            transaction.setToIban(savedAccount.getIban());
            transaction.setAmount(request.getInitialDeposit());
            transaction.setCurrency(moneyTypeEnum);
            transaction.setType(TransactionType.DEPOSIT);
            transaction.setStatusEnum(TransactionStatusEnum.SUCCESS);
            transactionRepository.save(transaction);
        }

        /*
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setIban(saveAccount.getIban());
        accountResponse.setCurrency(saveAccount.getCurrency());
        accountResponse.setBalance(saveAccount.getBalance());
        accountResponse.setCustomerId(saveAccount.getCustomer().getId());
        accountResponse.setId(saveAccount.getId());
        accountResponse.setInitialDeposit(saveAccount.getBalance());

         */

        return accountMapper.toResponse(savedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        accountRepository.delete(account);
    }

    @Override
    public AccountResponse getAccountByIban(String iban) {
        Account account = accountRepository.findByIban(iban)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        /*AccountResponse accountResponse = new AccountResponse();
        accountResponse.setIban(account.getIban());
        accountResponse.setCurrency(account.getCurrency());
        accountResponse.setBalance(account.getBalance());
        accountResponse.setCustomerId(account.getCustomer().getId());
        accountResponse.setId(account.getId());
        accountResponse.setInitialDeposit(account.getBalance());
        return accountResponse;
         */
        return accountMapper.toResponse(account);
    }

    @Override
    public List<AccountResponse> getAccounts() {
        /*
        List<Account> accounts = accountRepository.findAll();
        List<AccountResponse> accountResponseList = new ArrayList<>();
        for(Account account : accounts) {
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setIban(account.getIban());
            accountResponse.setCurrency(account.getCurrency());
            accountResponse.setBalance(account.getBalance());
            accountResponse.setCustomerId(account.getCustomer().getId());
            accountResponse.setId(account.getId());
            accountResponse.setInitialDeposit(account.getBalance());
            accountResponseList.add(accountResponse);
        }
        return accountResponseList;
         */
        List<Account> accounts = accountRepository.findAll();
        return accountMapper.toResponseList(accounts);

    }

    private String generateIban() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
