package com.bbva.wshomebanking.infrastructure.repositories.springdatajpa;

import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientAccountSpringRepository extends JpaRepository<ClientAccountEntity, ClientAccountId> {
}
