package com.aslankorkmaz.Core.Banking.API.dto.transaction;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    private String fromIban;
    private String toIban;
    private BigDecimal amount;
    private String currency;
    private String description;

}
