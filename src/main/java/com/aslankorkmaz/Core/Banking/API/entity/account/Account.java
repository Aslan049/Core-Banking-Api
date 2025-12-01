package com.aslankorkmaz.Core.Banking.API.entity.account;

import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
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

    @Column(name="iban", unique = true, nullable = false)
    private String iban;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name="currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private MoneyTypeEnum currency;

    @Column(name="balance")
    private BigDecimal balance = BigDecimal.ZERO;


    @Enumerated(EnumType.STRING)
    private AccountStatus status  = AccountStatus.ACTIVE;

    @Version
    private Long version;
}
