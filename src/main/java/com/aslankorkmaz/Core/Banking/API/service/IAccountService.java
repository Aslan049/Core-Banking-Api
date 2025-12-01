package com.aslankorkmaz.Core.Banking.API.service;

import com.aslankorkmaz.Core.Banking.API.dto.account.AccountCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.account.AccountResponse;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    public AccountResponse createAccount(AccountCreateRequest request);
}
