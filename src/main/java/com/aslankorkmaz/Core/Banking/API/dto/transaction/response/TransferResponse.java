package com.aslankorkmaz.Core.Banking.API.dto.transaction.response;

import com.aslankorkmaz.Core.Banking.API.entity.account.Currency;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionStatusEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {

    private Long id;
    private String fromIban;
    private String toIban;
    private BigDecimal amount;

    private Currency currency;

    private TransactionType type;

    private TransactionStatusEnum statusEnum;
    private String description;
    private Instant createdAt;

    private Long fromIbanId;
    private Long toIbanId;

}
