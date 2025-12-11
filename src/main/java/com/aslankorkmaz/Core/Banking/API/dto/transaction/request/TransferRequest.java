package com.aslankorkmaz.Core.Banking.API.dto.transaction.request;

import com.aslankorkmaz.Core.Banking.API.entity.account.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    @NotBlank
    private String fromIban;
    @NotBlank
    private String toIban;
    @NotNull(message = "Amount must not be empty")
    private BigDecimal amount;
    private Currency currency;
    private String description;

}
