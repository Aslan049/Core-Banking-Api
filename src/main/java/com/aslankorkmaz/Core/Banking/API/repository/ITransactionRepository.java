package com.aslankorkmaz.Core.Banking.API.repository;

import com.aslankorkmaz.Core.Banking.API.entity.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {
}
