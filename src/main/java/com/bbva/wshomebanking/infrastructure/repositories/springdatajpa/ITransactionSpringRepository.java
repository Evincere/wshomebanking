package com.bbva.wshomebanking.infrastructure.repositories.springdatajpa;

import com.bbva.wshomebanking.infrastructure.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionSpringRepository extends JpaRepository<TransactionEntity, Integer> {
}
