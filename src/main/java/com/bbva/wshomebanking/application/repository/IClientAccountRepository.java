package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IClientAccountRepository extends JpaRepository<ClientAccountEntity, UUID> {

}
