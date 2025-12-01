package com.aslankorkmaz.Core.Banking.API.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AccountResponse {

    private Long id;
    private Long customerId;
    private String iban;
    private String currency;
    private BigDecimal initialDeposit;
    private BigDecimal balance;
}
