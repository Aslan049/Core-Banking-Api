package com.aslankorkmaz.Core.Banking.API.entity.transaction;

import com.aslankorkmaz.Core.Banking.API.entity.account.Currency;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private String fromIban;
    private String toIban;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum statusEnum;


    private Instant createdAt = Instant.now();

    private String description;

}
