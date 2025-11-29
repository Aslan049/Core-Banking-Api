package com.aslankorkmaz.Core.Banking.API.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="iban", unique = true)
    private String iban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name="currency")
    private String currency;

    @Column(name="balance")
    private BigDecimal balance;

    private enum statusEnum {
        ACTIVE,
        LOCKED,
        CLOSED
    }

}
