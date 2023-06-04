package com.bbva.wshomebanking.infrastructure.repositories.springdatajpa;

import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAccountSpringRepository extends JpaRepository<AccountEntity, UUID> {

}
