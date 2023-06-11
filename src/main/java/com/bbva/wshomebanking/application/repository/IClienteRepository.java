package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Cliente;

import java.util.List;
import java.util.UUID;

public interface IClienteRepository {

    Cliente saveClient(Cliente client);

    List<Cliente> getAll();

    Cliente findById(UUID id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndApellido(String email, String apellido);

}
