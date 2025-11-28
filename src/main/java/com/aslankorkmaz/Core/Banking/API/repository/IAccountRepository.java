package com.aslankorkmaz.Core.Banking.API.repository;

import com.aslankorkmaz.Core.Banking.API.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
}
