package com.aslankorkmaz.Core.Banking.API.dto.transaction.request;

import com.aslankorkmaz.Core.Banking.API.entity.account.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DepositRequest {

    @NotNull
    private String toIban;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    private String description;
}
