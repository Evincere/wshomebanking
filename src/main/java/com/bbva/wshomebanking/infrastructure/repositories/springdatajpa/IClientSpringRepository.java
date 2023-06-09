package com.bbva.wshomebanking.infrastructure.repositories.springdatajpa;

import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface IClientSpringRepository extends JpaRepository<ClientEntity, Integer> {

    boolean existsByEmail(String email);

    boolean existsByPersonalId(String personalId);
    Optional<ClientEntity> findByPersonalId(String personalId);

}
