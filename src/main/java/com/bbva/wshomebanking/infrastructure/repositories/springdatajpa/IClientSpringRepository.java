package com.bbva.wshomebanking.infrastructure.repositories.springdatajpa;

import com.bbva.wshomebanking.infrastructure.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IClientSpringRepository extends JpaRepository<ClienteEntity, UUID> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndLastName(String email,
                                     String lastName);

}
