package com.aslankorkmaz.Core.Banking.API.repository;

import com.aslankorkmaz.Core.Banking.API.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {

}
