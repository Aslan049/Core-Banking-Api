package com.aslankorkmaz.Core.Banking.API.dto.transaction;

import com.aslankorkmaz.Core.Banking.API.entity.account.MoneyTypeEnum;
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
    private MoneyTypeEnum currency;
    private String description;
}
