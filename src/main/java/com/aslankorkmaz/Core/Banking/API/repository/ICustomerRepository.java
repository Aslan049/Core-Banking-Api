package com.aslankorkmaz.Core.Banking.API.repository;

import com.aslankorkmaz.Core.Banking.API.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByIdentityNumber(String identityNumber);
}
