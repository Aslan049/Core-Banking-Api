package com.aslankorkmaz.Core.Banking.API.dto.transaction.request;

import com.aslankorkmaz.Core.Banking.API.entity.account.Currency;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawRequest {


    private TransactionType type;

    @NotNull
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotBlank
    private String fromIban;
    private String description;

}
