package com.aslankorkmaz.Core.Banking.API.dto.transaction;

import com.aslankorkmaz.Core.Banking.API.entity.account.MoneyTypeEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionStatusEnum;
import com.aslankorkmaz.Core.Banking.API.entity.transaction.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {

    private Long id;
    private String fromIban;
    private String toIban;
    private BigDecimal amount;

    private MoneyTypeEnum currency;

    private TransactionType type;

    private TransactionStatusEnum statusEnum;
    private String description;
    private Instant createdAt;
    private BigDecimal updatedBalance;

}
