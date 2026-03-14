package com.aslankorkmaz.Core.Banking.API.mapper;

import com.aslankorkmaz.Core.Banking.API.dto.account.AccountCreateRequest;
import com.aslankorkmaz.Core.Banking.API.dto.account.AccountResponse;
import com.aslankorkmaz.Core.Banking.API.entity.account.Account;
import com.aslankorkmaz.Core.Banking.API.entity.account.AccountStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "balance",
            expression = "java(request.getInitialDeposit() == null ? java.math.BigDecimal.ZERO : request.getInitialDeposit())")
    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "iban", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "version", ignore = true)
    Account toEntity(AccountCreateRequest request);

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "initialDeposit", source = "balance")
    AccountResponse toResponse(Account account);

    List<AccountResponse> toResponseList(List<Account> accounts);

    @Mapping(target = "customer.id", source = "customerId")
    @Mapping(target = "balance", source = "initialDeposit")
    @Mapping(target = "iban", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateAccountfromRequest(AccountCreateRequest request, @MappingTarget Account account);
}

