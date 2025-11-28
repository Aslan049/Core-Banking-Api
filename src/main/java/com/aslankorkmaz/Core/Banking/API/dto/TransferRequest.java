package com.aslankorkmaz.Core.Banking.API.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequest {

    private String fromIban;
    private String toIban;
    private Double amount;
    private String currency;
}
