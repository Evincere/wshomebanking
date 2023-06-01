package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Cliente;

import java.util.List;
import java.util.UUID;

public interface IClienteRepository {

    Cliente saveCliente(Cliente cliente);

    List<Cliente> getAll();

    Cliente findById(UUID id);

    boolean existsByEmail(String email);
/*
    boolean existsByEmailAndLastNameAndFirstName(String email,
                                                 String apellido,
                                                 String nombre);
*/
    Cliente update(Cliente client);

}
