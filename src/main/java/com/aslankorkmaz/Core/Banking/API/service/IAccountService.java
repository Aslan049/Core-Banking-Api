package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.account.AccountCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.account.AccountResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAccountService {
    public AccountResponse createAccount(AccountCreateRequest request);
    public void deleteAccount(Long id);
    public AccountResponse getAccountByIban(String iban);
    public List<AccountResponse> getAccounts();
}
