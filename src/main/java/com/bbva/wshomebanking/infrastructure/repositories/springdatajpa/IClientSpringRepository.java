package com.bbva.wshomebanking.infrastructure.repositories.springdatajpa;

import com.bbva.wshomebanking.infrastructure.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClientSpringRepository extends JpaRepository<ClienteEntity, UUID> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndApellido(String email,
                                     String apellido);

}
