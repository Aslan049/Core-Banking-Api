package com.aslankorkmaz.Core.Banking.API.dto.account;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {

    @NotNull
    private Long customerId;
    @NotNull
    private String currency;
    private BigDecimal initialDeposit;
}
