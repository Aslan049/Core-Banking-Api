package com.aslankorkmaz.Core.Banking.API.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromIban;
    private String toIban;
    private BigDecimal amount;
    private String currency;

    private enum typeEnum {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    }
    private enum statusEnum {
        PENDING,
        SUCCESS,
        FAILED
    }

    private LocalDateTime createdAt = LocalDateTime.now();

    private String description;

}
