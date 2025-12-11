package com.aslankorkmaz.Core.Banking.API.repository;

import com.aslankorkmaz.Core.Banking.API.entity.account.Account;
import com.aslankorkmaz.Core.Banking.API.entity.account.Currency;
import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByIban(String iban);
    boolean existsByCustomerAndCurrency(Customer customer, Currency currency);

}
