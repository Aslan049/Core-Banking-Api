package com.aslankorkmaz.Core.Banking.API.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawRequest {

    private String fromIban;
    private BigDecimal amount;
    private String currency;

}
