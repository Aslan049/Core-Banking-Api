package com.aslankorkmaz.Core.Banking.API.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequest {

    private Long customerId;
    private String currency;
    private Double initialDeposit;
}
