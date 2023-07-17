package com.bbva.wshomebanking.infrastructure.repositories;

import com.bbva.wshomebanking.application.repository.IClientAccountRepository;
import com.bbva.wshomebanking.application.repository.IClientRepository;
import com.bbva.wshomebanking.domain.models.Account;
import com.bbva.wshomebanking.domain.models.Client;
import com.bbva.wshomebanking.domain.models.ClientAccount;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountEntity;
import com.bbva.wshomebanking.infrastructure.entities.ClientAccountId;
import com.bbva.wshomebanking.infrastructure.entities.ClientEntity;
import com.bbva.wshomebanking.infrastructure.entities.AccountEntity;
import com.bbva.wshomebanking.infrastructure.mapper.AccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientAccountEntityMapper;
import com.bbva.wshomebanking.infrastructure.mapper.ClientEntityMapper;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IAccountSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientAccountSpringRepository;
import com.bbva.wshomebanking.infrastructure.repositories.springdatajpa.IClientSpringRepository;
import com.bbva.wshomebanking.presentation.request.client.ClientFindRequest;
import com.bbva.wshomebanking.presentation.response.client.ClientFindResponse;
import com.bbva.wshomebanking.utilities.ErrorCodes;
import com.bbva.wshomebanking.utilities.exceptions.ErrorWhenSavingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {

    private final IClientSpringRepository clienteSpringRepository;
    private final IAccountSpringRepository accountSpringRepository;
    private final IClientAccountSpringRepository clientAccountSpringRepository;
    private final ClientEntityMapper clientEntityMapper;
    private final AccountEntityMapper accountEntityMapper;
    private final ClientAccountEntityMapper clientAccountEntityMapper;


    @Override
    public Client saveClient(Client client) throws ErrorWhenSavingException {

        try {
            // save the client
            ClientEntity clientEntity = clientEntityMapper.domainToEntity(client);
            ClientEntity savedClient = clienteSpringRepository.save(clientEntity);
            // save the account
            AccountEntity accountEntity = accountEntityMapper.domainToEntity(client.getAccounts().get(0).getAccount());
            AccountEntity savedAccount = accountSpringRepository.save(accountEntity);
            // save the relationship
            ClientAccountEntity clientAccountEntity = ClientAccountEntity.builder()
                    .id(new ClientAccountId(savedAccount.getId(),savedClient.getId()))
                    .client(savedClient)
                    .account(savedAccount)
                    .holderType(client.getAccounts().get(0).getAccountHolderType())
                    .build();
            clientAccountSpringRepository.save(clientAccountEntity);

            // set the relationship to the client
            List<ClientAccountEntity> clientAccountEntityList = new ArrayList<ClientAccountEntity>();
            clientAccountEntityList.add(clientAccountEntity);
            savedClient.setClientAccounts(clientAccountEntityList);

            return clientEntityMapper.entityToDomain(savedClient);

        } catch(Exception e) {
            throw new ErrorWhenSavingException("No se pudo guardar el cliente");
        }

    }

    @Override
    public Client updateClient(Client client) throws ErrorWhenSavingException {
        try {
            ClientEntity clientEntity = clientEntityMapper.domainToEntity(client);
            ClientEntity savedClient = clienteSpringRepository.save(clientEntity);
            return clientEntityMapper.entityToDomain(savedClient);
        } catch (Exception e) {
            throw new ErrorWhenSavingException(ErrorCodes.COULD_NOT_UPDATE_CLIENT);
        }
    }

    @Override
    public boolean existsByPersonalId(String personalId) { return clienteSpringRepository.existsByPersonalId(personalId); }

    @Override
    public Optional<Client> findById(int id) {
        Optional<ClientEntity> optionalClient = clienteSpringRepository.findById(Integer.valueOf(id));
        if (optionalClient.isEmpty()) {
            return Optional.empty();
        }

        return optionalClient.map(clientEntityMapper::entityToDomain);
    }

    @Override
    public List<Client> findAll() {
        List<ClientEntity> clientEntityList = new ArrayList<>();
        List<Client> clientResponseList = new ArrayList<>();

        clientEntityList = clienteSpringRepository.findAll();

        for (ClientEntity entity : clientEntityList) {
            clientResponseList.add(clientEntityMapper.entityToDomain(entity));
        }

        return clientResponseList;
    }

    @Override
    public Optional<Client> findByPersonalId(String personalId) {
        Optional<ClientEntity> optionalClient = clienteSpringRepository.findByPersonalId(personalId);
        if (optionalClient.isEmpty()) {
            return Optional.empty();
        }

        return optionalClient.map(clientEntityMapper::entityToDomain);
    }



}
