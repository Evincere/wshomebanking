package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientRepository {

    Client saveClient(Client cliente);

    List<Client> getAll();

    Optional<Client> findById(int id);

    boolean existsByEmail(String email);
    boolean existsByPersonalId(String personalId);

    Client update(Client client);

}
