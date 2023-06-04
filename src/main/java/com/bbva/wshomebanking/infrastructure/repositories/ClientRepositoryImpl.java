package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {

    private final IClientSpringRepository clienteSpringRepository;
    private final ClientEntityMapper clientEntityMapper;


    @Override
    public Client saveCliente(Client cliente) {
        ClientEntity clientEntity = clientEntityMapper.domainToEntity(cliente);

        clienteSpringRepository.save(clientEntity);

        return clientEntityMapper.entityToDomain(clientEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clienteSpringRepository.existsByEmail(email);
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public Client findById(UUID id) {
        return null;
    }



    @Override
    public Client update(Client client) {
        return null;
    }
}
