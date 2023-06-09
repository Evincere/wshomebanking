package com.bbva.wshomebanking.application.repository;

import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientRepository {

    Client saveClient(Client cliente) throws ErrorWhenSavingException;

    Client updateClient(Client client) throws ErrorWhenSavingException;

    List<Client> getAll();

    Optional<Client> findById(int id);
    Optional<Client> findByPersonalId(String personalId);

    boolean existsByPersonalId(String personalId);

}
